const mazetasQueue = [];
let movement = 10;

window.onload = () => {
    const app = new PIXI.Application({
        width: 1000, // old was 750
        height: 680, // old was 550
        transparent: false,
        forceCanvas: true,
        backgroundColor: 0xFFFFFF
    });
    document.body.appendChild(app.view);
    PIXI.loader
        .add('mazeta.png')
        .add('mazeta_seca.png')
        .load(() => {
            addMazetas(app);
            setInterval(moove, 1000);
        });
};

function moove() {
    for(let i = 0; i < mazetasQueue.length; i++) {
        const current = mazetasQueue.shift();
        mazetasQueue.push(current);
        current.y += movement;
    }
    movement = movement * -1;
}

function addMazetas(app) {
    const columns = 4;
    const lines = 4;
    const mazetaSize = {
        x: 117,
        y: 63,
    };
    const basePosition = {
        x: 0,
        y: 0,
    };
    console.log(basePosition);
    let line = 0;
    let column = columns;
    for(let i = 0; i < lines; i++) {
        basePosition.x = mazetaSize.x * (--column);
        basePosition.y = mazetaSize.y * (line++);
        for(let j = 0; j < columns; j++) {
            const mazeta = new PIXI.Sprite(PIXI.loader.resources["mazeta.png"].texture);
            mazetasQueue.push(mazeta);
            mazeta.x = basePosition.x + (mazetaSize.x * j);
            mazeta.y = basePosition.y + (mazetaSize.y * j);
            app.stage.addChild(mazeta);
        }
    }
}
