var algorytm = function(computerLeftCards,playerLeftCards,pcTurn,playerPlayedCard) {
    realJsString = computerLeftCards + "";
    array = realJsString.split("|");
    return Math.floor(Math.random()*array.length);
    };