var playerTurnNow = "";

function playerTurn(id) {
    if (playerTurnNow != playerType) {
        alert("It's not your turn!")
    } else {
        if ((playerType == "FIRST_PLAYER" && id > 7) || (playerType == "SECOND_PLAYER" && id < 7)) {
            alert("choose from your pits!")
            return;
        }
        var stones = $("#" + id).text();
        if (stones != "0") {
            makeAMove(id);
        }

    }
}

function makeAMove(id) {
    $.ajax({
        url: url + "/game/sow",
        type: 'POST',
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify({
            "gameId": gameId,
            "pitIndex": id
        }),
        success: function (data) {
            refreshGameBoard(data);
        },
        error: function (error) {
            console.log(error);
        }
    })
}

function refreshGameBoard(data) {


    let pits = data.pits;
    for (let i = 0; i < pits.length; i++) {
        $("#pit_" + i).text(pits[i].stones);
    }
    if (data.winner != null) {
        alert("Winner is " + data.winner.name);
    }
    playerTurnNow = data.playerTurn;

    $("#firstPlayerName").text(data.firstPlayer.name + "'s larger pit");
    $("#secondPlayerName").text(data.secondPlayer== null ? "second player" : data.secondPlayer.name + "'s larger pit");

    if (playerType == "FIRST_PLAYER") {
        $("#firstPlayerName").background="#1472a9";
        $("#secondPlayerName").background="#333";
        $("#opponentLogin").text(data.secondPlayer!= null ? data.secondPlayer.name : "");

    } else {
        $("#secondPlayerName").background ="#1472a9";
        $("#firstPlayerName").background="#333";
        $("#opponentLogin").text(data.firstPlayer.name);

    }

}


$(".pitValue").click(function () {
    var pitId = $(this).attr('id');
    playerTurn(parseInt(pitId.substring(4)) + 1);
});




