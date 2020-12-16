import CommandReader from "./CommandReader";

export default class ConsoleIO {
  constructor(elementId = 'console-io') {
    this.elementId = elementId;
    this.commandHandlers = {};
  }

  mount() {
    this.consoleIO = document.getElementById(this.elementId);
    this.commandReader = new CommandReader(this.consoleIO);
    this.commandReader.onEnterPressed(this.dispatch.bind(this));
  }

  write(str = '') {
    this.consoleIO.innerHTML += str;
  }

  startHandlingKeyEvents() {
    this.commandReader.startHandlingKeyEvents();
  }

  onCommand(commandName, handler) {
    this.commandHandlers[commandName] = handler;
  }

  async dispatch(command, args) {
    const handler = this.commandHandlers[command];
    if (!handler) {
      this.writeColor('Command not found!', 'red');
      this.writeln();
      return;
    }
    await handler.handle(args);
  }

  writeln(str = '') {
    this.write(str);
    this.write('<br/>');
  }

  clear() {
    this.consoleIO.innerHTML = '';
  }

  writeColor(str = '', color = 'white') {
    this.write(`<span style='color:${color}'>${str}</span>`);
  }
}
