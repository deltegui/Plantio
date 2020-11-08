/* eslint-disable no-param-reassign */

const ignore = () => {};

const keyHandlers = {
  Shift: ignore,
  Meta: ignore,
  Control: ignore,
  ArrowUp: ignore,
  ArrowDown: ignore,
  ArrowLeft: ignore,
  ArrowRight: ignore,
};

export default class CommandReader {
  constructor(commandRootElement) {
    this.consoleIO = commandRootElement;
    this.readedCommand = '';
    this.handleEnter = () => {};
  }

  startHandlingKeyEvents() {
    this.consoleIO.innerHTML += '> ';
    document.onkeydown = (evt) => {
      evt.preventDefault();
      if (evt.key === 'Backspace') {
        this.handleBackspace();
        return;
      }
      if (evt.key === 'Enter') {
        this.consoleIO.innerHTML += '<br />';
        this.emitCommand(this.readedCommand);
        this.consoleIO.innerHTML += '> ';
        this.consoleIO.scrollTop = this.consoleIO.scrollHeight;
        this.readedCommand = '';
        return;
      }
      if (keyHandlers[evt.key]) {
        keyHandlers[evt.key](this.consoleIO);
        return;
      }
      this.readedCommand += evt.key;
      this.consoleIO.innerHTML += evt.key;
    };
  }

  handleBackspace() {
    if (this.consoleIO.innerText.endsWith('> ')) {
      return;
    }
    this.consoleIO.innerHTML = this.consoleIO.innerHTML.slice(0, -1);
    this.readedCommand = this.readedCommand.slice(0, -1);
  }

  onEnterPressed(callback) {
    this.handleEnter = callback;
  }

  emitCommand(command) {
    const args = command.split(' ');
    const name = args.shift();
    this.handleEnter(name, args);
  }
}
