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
    static get Corner() {
        return {
            TopLeft: {x: 0, y: 0},
            TopRight: {x: CultivoCoordinate.MaxValue.x, y: 0 },
            BottomLeft: {x: 0, y: CultivoCoordinate.MaxValue.y},
            BottomRight: CultivoCoordinate.MaxValue,
        };
    }

    static get Border() {
        return {
            Top: 'top',
            Bottom: 'bottom',
            Left: 'left',
            Right: 'right',
        };
    }

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

    isInCorner(corner) {
        return this.x === corner.x && this.y === corner.y;
    }

    isInBorder(border) {
        switch(border) {
        case CultivoCoordinate.Border.Top: return this.y === 0;
        case CultivoCoordinate.Border.Bottom: return this.y === CultivoCoordinate.MaxValue.x;
        case CultivoCoordinate.Border.Left: return this.x === 0;
        case CultivoCoordinate.Border.Right: return this.x === CultivoCoordinate.MaxValue.y;
        default: return false;
        }
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
        this.queue = [];
    }

    load() {
        this.game.load.image('mazeta', 'mazeta.png');
        this.game.load.image('mazetaSeca', 'mazeta_seca.png');
    }

    create() {
        this._createMatrix();
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
            this.queue.push(mazeta);
        }
    }

    update() {
    }
}

class Mazeta {

    static get size() {
        return {
            x: 117,
            y: 63,
        };
    }

    /**
     * @param {Phaser.Game} game
     * @param {CultivoCoordinate} matrixIndex
     */
    constructor(game, coordinate) {
        this.game = game;
        this.coordinate = coordinate;
        this.offset = new Phaser.Geom.Point(0, 30);
    }

    create() {
        this.position = this.coordinate.toRealPosition();
        console.log(this.position);
        this._createShadow();
        this._createSprite();
    }

    _createShadow() {
        //const shadowOffset = this._calculateShadowOffset();
        const shadowOffset = this.offset;
        const shadow = this.game.add.sprite(this.position.x + shadowOffset.x, this.position.y + shadowOffset.y, 'mazeta').setOrigin(0, 0);
        shadow.tint = 0x000000;
        shadow.alpha = 0.6;
        //shadow.displayWidth = shadow.displayWidth - this._calculateWidthDecrement();
        shadow.displayWidth = shadow.displayWidth - 20;
    }

    _calculateShadowOffset() {
        if(this.coordinate.isInBorder(CultivoCoordinate.Border.Bottom) && !this.coordinate.isInCorner(CultivoCoordinate.Corner.BottomLeft)) {
            return {
                x: this.offset.x + 20,
                y: this.offset.y,
            };
        }
        return this.offset;
    }

    _calculateWidthDecrement() {
        if(this.coordinate.isInBorder(CultivoCoordinate.Border.Bottom) && !this.coordinate.isInCorner(CultivoCoordinate.Corner.BottomLeft)) {
            return 50
        }
        return 20;
    }

    _createSprite() {
        this.game.add.sprite(this.position.x, this.position.y, 'mazeta').setOrigin(0, 0);
    }
}