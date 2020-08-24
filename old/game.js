const config = {
    type: Phaser.AUTO,
    width: 1024,
    height: 1024,
    scene: {
        preload,
        create,
        update,
    }
};

const game = new Phaser.Game(config);
let cultivo;

function preload() {
    cultivo = new Cultivo(this);
    cultivo.load();
    this.cameras.main.setBackgroundColor('#FFFFFF');
}

function create() {
    cultivo.create();
}

function update() {
}

class CultivoCoordinate {

    static get MaxSize() {
        return {
            x: 4,
            y: 4,
        };
    }

    static get MaxValue() {
        const size = CultivoCoordinate.MaxSize;
        return {
            x: size.x - 1,
            y: size.y - 1,
        };
    }

    constructor(x, y) {
        console.log(`Created coordinate { ${x}, ${y} }`);
        this.x = x;
        this.y = y;
    }

    toRealPosition() {
        const linePos = this._calculateLinePosition();
        const yOffset = this.x * Mazeta.size.y;
        return {
            x: linePos.x + (this.x * Mazeta.size.x),
            y: yOffset + this.y * Mazeta.size.y,
        };
    }

    _calculateLinePosition() {
        const lineStartOffset = CultivoCoordinate.MaxValue.y - this.y;
        return {
            x: lineStartOffset * Mazeta.size.x,
            y: this.y * Mazeta.size.y,
        };
    }
}

class Cultivo {

    static get size() {
        return CultivoCoordinate.MaxSize;
    }

    constructor(game) {
        this.game = game;
        this.mazetas = [];
    }

    load() {
        this.game.load.image('mazeta', 'mazeta.png');
        this.game.load.image('mazetaSeca', 'mazeta_seca.png');
    }

    create() {
        this._createMatrix();
        this._createShadow();
        setInterval(this.move.bind(this), 1000);
    }

    _createMatrix() {
        for(let i = 0; i < Cultivo.size.x; i++) {
            this._createLine(i);
        }
    }

    _createLine(lineNumber) {
        for(let i = 0; i < Cultivo.size.y; i++) {
            const coordinate = new CultivoCoordinate(lineNumber, i);
            const mazeta = new Mazeta(this.game, coordinate);
            mazeta.create();
            this.mazetas.push(mazeta);
        }
    }

    move() {
        for(let i = 0; i < this.mazetas.length; i++) {
            const current = this.mazetas[i];
            current.move();
        }
    }
}

class Mazeta {

    static get size() {
        return {
            x: 117,
            y: 63,
        };
    }

    constructor(game, coordinate) {
        this.game = game;
        this.coordinate = coordinate;
        this.offset = new Phaser.Geom.Point(0, 30);
        this.movement = 10;
        this.shadow = null;
        this.sprite = null;
    }

    create() {
        this.position = this.coordinate.toRealPosition();
        console.log(this.position);
        this._createSprite();
    }

    _createSprite() {
        this.sprite = this.game.add.sprite(this.position.x, this.position.y, 'mazeta').setOrigin(0, 0);
    }

    move() {
        this.sprite.y += this.movement;
        this.movement = this.movement * -1;
    }
}