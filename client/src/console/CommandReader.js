/* eslint-disable no-param-reassign */
/* eslint-disable class-methods-use-this */

const keyHandlers = {
};

const ignoreKeys = [
  'Shift',
  'Meta',
  'Control',
  'ArrowUp',
  'ArrowDown',
  'ArrowLeft',
  'ArrowRight',
  'Alt',
  'OS',
  'F1',
  'F2',
  'F3',
  'F4',
  'F5',
  'F6',
  'F7',
  'F8',
  'F9',
  'F10',
  'F11',
  'F12',
  'Tab',
  'CapsLock',
  'AltGraph',
  'ContextMenu',
  'Insert',
  'PageDown',
  'Delete',
  'Home',
  'End',
  'PageUp',
  'Pause',
  'ScrollLock',
  'Dead',
];

export default class CommandReader {
  constructor(commandRootElement) {
    this.consoleIO = commandRootElement;
    this.readedCommand = '';
    this.enterPressed = () => {};
    keyHandlers.Backspace = this.handleBackspace.bind(this);
    keyHandlers.Enter = this.handleEnter.bind(this);
  }

  startHandlingKeyEvents() {
    this.consoleIO.innerHTML += '> ';
    document.onkeydown = this.handleKeyEvent.bind(this);
  }

  stopHandlingKeyEvents() {
    document.onkeydown = undefined;
  }

  handleKeyEvent(evt) {
    if (ignoreKeys.includes(evt.key)) {
      return;
    }
    evt.preventDefault();
    if (keyHandlers[evt.key]) {
      keyHandlers[evt.key](this.consoleIO);
      return;
    }
    this.readedCommand += evt.key;
    this.consoleIO.innerHTML += evt.key;
  }

  handleBackspace() {
    if (this.consoleIO.innerText.endsWith('> ')) {
      return;
    }
    this.consoleIO.innerHTML = this.consoleIO.innerHTML.slice(0, -1);
    this.readedCommand = this.readedCommand.slice(0, -1);
  }

  async handleEnter() {
    this.consoleIO.innerHTML += '<br />';
    if (this.readedCommand.length !== 0) {
      await this.emitCommand(this.readedCommand);
    }
    this.consoleIO.innerHTML += '> ';
    this.consoleIO.scrollTop = this.consoleIO.scrollHeight;
    this.readedCommand = '';
  }

  onEnterPressed(callback) {
    this.enterPressed = callback;
  }

  async emitCommand(command) {
    const args = command.split(' ').filter((str) => str.length !== 0);
    const name = args.shift();
    await this.enterPressed(name, args);
  }
}
