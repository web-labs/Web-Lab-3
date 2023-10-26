let canvas = document.querySelector('#canvas');
let ctx = canvas.getContext('2d');
const points = [];

function drawDot(ctx, x, y, radius, startAngle, endAngle){
    ctx.beginPath();
    ctx.arc(x, y, radius, startAngle, endAngle);
    ctx.fill();
}

function putDots(ctx) {
    ctx.fillStyle = "black";
    const radius = 4;
    drawDot(ctx, 262.5, 175, radius, 0, Math.PI * 2);
    drawDot(ctx, 345, 175, radius, 0, Math.PI * 2);
    drawDot(ctx, 175, 5, radius, 0, Math.PI * 2);
    drawDot(ctx, 175, 87.5, radius, 0, Math.PI * 2);
    drawDot(ctx, 87.5, 175, radius, 0, Math.PI * 2);
    drawDot(ctx, 5, 175, radius, 0, Math.PI * 2);
    drawDot(ctx, 175, 262.5, radius, 0, Math.PI * 2);
    drawDot(ctx, 175, 345, radius, 0, Math.PI * 2);

}
function drawPoint(x, y, r, result){
    if (r === undefined){
        r = 0;
    }
        const pointSize = 10;
        ctx.beginPath();
        const canvasCoords = toCanvasCoords(x, y, r, 350)
        ctx.arc(canvasCoords.x, canvasCoords.y, pointSize/2, 0, Math.PI * 2);
        ctx.fillStyle = result ? 'green' : 'red';
        ctx.fill();

}

function drawCoordsPlane(r){
    canvas.width = 350;
    canvas.height = 350;

    const halfWidth = canvas.width / 2;
    const halfHeight = canvas.height / 2;
    const quarterWidth = canvas.width / 4;
    const quarterHeight = canvas.height / 4;
    const arrowSize = 10;


    ctx.fillStyle = '#4169E1';
    //1st quarter - square
    ctx.fillRect(halfWidth, 0, halfWidth, halfHeight);

    //2nd quarter - 1/4 circle
    ctx.beginPath();
    ctx.arc(halfWidth, halfHeight, quarterWidth, Math.PI, 1.5*Math.PI);
    ctx.lineTo(halfWidth, halfHeight);
    ctx.fill();

    //1st quarter - triangle

    ctx.beginPath();
    ctx.moveTo(canvas.width, halfHeight);
    ctx.lineTo(halfWidth, 0);
    ctx.lineTo(halfWidth, halfHeight+quarterHeight);
    ctx.closePath();
    ctx.fill();


    ctx.beginPath();
    putDots(ctx);
    ctx.font = "15px Arial";
    ctx.fillText('y', 150, 15);
    ctx.fillText('x', 340, 195);
    ctx.fillText(String(r/2), halfWidth+quarterWidth, halfHeight-10);
    ctx.fillText(r, 322, halfHeight-10);
    ctx.fillText(r, 190, 15);
    ctx.fillText(String(-r/2), 87.5, halfHeight-10);
    ctx.fillText(String(-r), 0, halfHeight-10);

    ctx.fillText(String(-r), 187, canvas.height);
    ctx.fillText(String(-r/2), 187, 262.5);

    // Axes
    ctx.beginPath();
    ctx.moveTo(0, halfHeight);
    ctx.lineTo(canvas.width, halfHeight);
    ctx.moveTo(halfWidth, 0);
    ctx.lineTo(halfWidth, canvas.height);

    ctx.strokeStyle = "black";
    ctx.stroke();

    // Стрелочки для осей
    ctx.moveTo(canvas.width - arrowSize, halfHeight - arrowSize);
    ctx.lineTo(canvas.width, halfHeight);
    ctx.lineTo(canvas.width - arrowSize, halfHeight + arrowSize);

    ctx.moveTo(halfWidth - arrowSize, arrowSize);
    ctx.lineTo(halfWidth, 0);
    ctx.lineTo(halfWidth + arrowSize, arrowSize);
    ctx.fillStyle = "black";

    ctx.fillText(String(r/2), 185, 87.5);
    ctx.stroke();
}

function toCanvasCoords(x, y, r, canvasSize) {
    const scale = canvasSize / 2;
    return {
        x: scale / r * x + scale,
        y: canvasSize - (scale / r * y + scale)
    };
}

function toNormalCoords(canvasX, canvasY, r, canvasSize){
    const scale = canvasSize / 2;
    return {
        x: r * (canvasX - scale) / scale,
        y: r * (canvasSize - canvasY - scale) / scale
    }
}


canvas.addEventListener('click', function(event){
    const rect = canvas.getBoundingClientRect();
    const x = event.clientX - rect.left;
    const y = event.clientY - rect.top;
    const r = document.getElementById('myForm:decimal').value;

    console.log(x, y, r)
    const normalCoords = toNormalCoords(x, y, r, 350);
    sendCoordinates(normalCoords.x, normalCoords.y, r);
    console.log(normalCoords.x, normalCoords.y, r)
})

function sendCoordinates(x, y) {
    document.getElementById('graph-form:xValue').value = x;
    document.getElementById('graph-form:yValue').value = y;
    // document.getElementById('myForm:rValue').value = r;
    // document.getElementById('myForm:hiddenButton').click();

    sendCoordinatesToServer();
}


