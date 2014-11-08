var c = document.getElementById("cnvs");
var ctx=c.getContext("2d");
var time = new Date();
var second = time.getSeconds();
var minute = time.getMinutes();
var hour = time.getHours();

var sdegrees = 0;
var mdegrees = 0;
var hdegrees = 0;

var img = new Image();
img.src = "clock.jpg";
ctx.drawImage(img,0,0,100,100);

function updatetime(){

	time = new Date();
	second = time.getSeconds();
	minute = time.getMinutes();
	hour = time.getHours();

	sdegrees = 6*second;
	mdegrees = 6*minute;
	hdegrees = 30*hour;

	ctx.clearRect(0,0,500,500);
	ctx.strokeStyle="#000000";




	ctx.save();

	ctx.beginPath();
	ctx.arc(50,50,5,0, 2*Math.PI);
	ctx.fill();
	ctx.closePath();
	
	ctx.beginPath();
	ctx.arc(50,50,50,0, 2*Math.PI);
	ctx.clip();
	ctx.drawImage(img,0,0,100,100);
	ctx.closePath();
	
	ctx.restore();

	ctx.translate(50,50);
	ctx.rotate(sdegrees*Math.PI/180);
	ctx.translate(-50,-50);

	ctx.lineWidth = 1;

	ctx.beginPath();
	ctx.moveTo(50,50);
	ctx.lineTo(50,0);
	ctx.stroke();
	ctx.closePath();
	
	ctx.translate(50,50);
	ctx.rotate(-(sdegrees*Math.PI/180));
	ctx.translate(-50,-50);



	ctx.translate(50,50);
	ctx.rotate(mdegrees*Math.PI/180);
	ctx.translate(-50,-50);

	ctx.lineWidth = 2;

	ctx.beginPath();
	ctx.moveTo(50,50);
	ctx.lineTo(50,10);
	ctx.stroke();
	ctx.closePath();

	ctx.translate(50,50);
	ctx.rotate(-(mdegrees*Math.PI/180));
	ctx.translate(-50,-50);







	ctx.translate(50,50);
	ctx.rotate(hdegrees*Math.PI/180);
	ctx.translate(-50,-50);

	ctx.lineWidth = 4;

	ctx.beginPath();
	ctx.moveTo(50,50);
	ctx.lineTo(50,20);
	ctx.stroke();
	ctx.closePath();

	ctx.translate(50,50);
	ctx.rotate(-(hdegrees*Math.PI/180));
	ctx.translate(-50,-50);


}
setInterval(function(){updatetime()}, 500);