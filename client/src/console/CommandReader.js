function createTyper() {
  const typer = document.createElement('textarea');
  typer.autocomplete = 'off';
  typer.spellcheck = false;
  typer.autofocus = true;
  typer.autocorrect = 'off';
  typer.style = 'position: absolute; top: 0px; left: -9999px';
  return typer;
}

const body = document.getElementsByTagName('body')[0];

const timestampComparer = {
  minimumTimePassed: 50,
  lastEventTimeStamp: 0,
  lastDifference: 0,

  updateDifference(evt) {
    this.lastDifference = evt.timeStamp - this.lastEventTimeStamp;
    this.lastEventTimeStamp = evt.timeStamp;
  },

  notPassedEnoughTime() {
    return this.lastDifference <= this.minimumTimePassed;
  },
};

export default class CommandReader {
  constructor(commandRootElement) {
    this.consoleIO = commandRootElement;
    this.typer = createTyper();
    this.readedCommand = '';
    this.enterPressed = () => {};
  }

  startHandlingKeyEvents() {
    this.focusConsole();
    this.consoleIO.innerHTML += '> ';
    body.appendChild(this.typer);
    this.consoleIO.addEventListener('click', this.focusConsole.bind(this));
    this.typer.addEventListener('input', this.handleKeyEvent.bind(this));
  }

  focusConsole() {
    this.typer.focus();
  }

  handleKeyEvent(evt) {
    evt.preventDefault();
    if (!this.typer.value) {
      this.handleBackspace();
      return;
    }
    const key = this.typer.value.charAt(this.typer.value.length - 1);
    if (key === '\n' || key === '\r\n') {
      this.handleEnter();
      return;
    }
    if (this.typer.value.length < this.readedCommand.length) {
      this.handleBackspace();
      return;
    }
    timestampComparer.updateDifference(evt);
    if (timestampComparer.notPassedEnoughTime()) {
      return;
    }
    this.readedCommand += key;
    this.consoleIO.innerHTML += key;
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
    this.typer.value = '';
  }

  onEnterPressed(callback) {
    this.enterPressed = callback;
  }

  async emitCommand(command) {
    const args = command.split(' ').filter((str) => str.length !== 0);
    const name = args.shift().toLowerCase();
    await this.enterPressed(name, args);
  }
}
