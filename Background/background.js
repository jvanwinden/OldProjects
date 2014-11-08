var color = 0;
function changebg(){
	color = '#' + Math.floor((Math.random()*16777215)).toString(16);
	$("html").css("background-color", color);
}
changebg();
setInterval(function(){changebg()}, 1000);