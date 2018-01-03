const BOARD_SIZE = 9;
var OFFSET;

$(document).ready(function(){
	
	OFFSET = (41.0 / 651.0) * $("#board").width();
	
	console.log("OFFSET: ");
	console.log(OFFSET);
	
	$("#board").mousemove(function(e) {
		var x = e.pageX;
		var y = e.pageY;

		if (!onBoard($(this), x, y)) {
			console.log("off board!");
			return;
		}

		var pos = getPos(this, x, y);
		var coord = getCoord(this, pos.i, pos.j);
		console.log(
			x + "," + y + " => " + 
			pos.j + "," + pos.i + " => " + 
			coord.x + "," + coord.y
		);

		hoverStone(this, pos.i, pos.j);
	});

});

function onBoard(obj, x, y) {
	var error = 0;
	var x0 = obj.offset().left;
	var y0 = obj.offset().top;
	var w = obj.width();
	var h = obj.width();

	if (x < x0 + OFFSET - error) {
		return false;
	}
	if (x > x0 + w - OFFSET + error) {
		return false;
	}
	if (y < y0 + OFFSET - error) {
		return false;
	}
	if (y > y0 + h - OFFSET + error) {
		return false;
	}

	return true;
}

/*
	@params: Obj, int, int
	@return: Obj[int, int]
*/
function getPos(board, x, y) {
	var w = $(board).width() - 2 * OFFSET;
	var h = $(board).height() - 2 * OFFSET;

	var y0 = $(board).offset().top + OFFSET;
	var x0 = $(board).offset().left + OFFSET;

	var j = Math.round(((x - x0) / w) * (BOARD_SIZE - 1));
	var i = Math.round(((y - y0) / h) * (BOARD_SIZE - 1));

	return {
		i: i,
		j: j,
	};
}

/*
	@params: Obj, int, int
	@return: float, float
*/
function getCoord(board, i, j) {
	var board = $(board);
	var w = board.width() - 2 * OFFSET;
	var h = board.height() - 2 * OFFSET;
	var x0 = board.offset().left;
	var y0 = board.offset().top;

	return {
		x: x0 + OFFSET + (w / (BOARD_SIZE - 1)) * j ,
		y: y0 + OFFSET + (h / (BOARD_SIZE - 1)) * i,
	};
}

function hoverStone(board, i, j) {
	$("#stone").remove();
	$('#stones').prepend("<img class='stone' id='stone' src='img/go-stone-black.png'>")

	var x0 = $(board).offset().left + OFFSET;
	var y0 = $(board).offset().top + OFFSET;
	var dw = ($(board).width() - 2 * OFFSET) / (BOARD_SIZE - 1);
	var dh = dw;

	$("#stone").css({
		width: dw,
		height: dh,
		top: y0 + i * dh - 0.9 * (dh / 2), 
		left: x0 + j * dw - 0.9 * (dw / 2), 
		position:'fixed'
	});
}