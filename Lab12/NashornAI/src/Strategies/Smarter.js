var algorytm = function(computerLeftCards,playerLeftCards,pcTurn,playerPlayedCard) {
    realJsString = computerLeftCards + "";
    array = realJsString.split("|");
    for(var i=0;i<array.length;i++)
        array[i]=Number(array[i]);
    realJsString2 = playerLeftCards + "";
    array2 = realJsString2.split("|");
    for(var i=0;i<array2.length;i++)
        array2[i]=Number(array2[i]);
    playerPlayedCard = Number(playerPlayedCard);
    print(array.toString());
    //jesli komputer zaczyna rozgrywke
    if(pcTurn === true){
        //sprawdza swoja najwyzsza karte
        var maxIndex = 0;
        var max = array[0];
        for (var i = 1; i < array.length; i++) {
            if (array[i] > max) {
                maxIndex = i;
                max = array[i];
            }
        }
        //sprawdza najwyzsza karte przeciwnika
        var max2 = array2[0];
        for (var j = 1; j < array2.length; j++) {
            if (array2[j] > max2) {
                max2 = array2[j];
            }
        }
        //jesli przeciwnik ma wyzsza najwyzsza karte, to wystawia swoja najnizsza
        if(max2 > max){
            var minIndex = 0;
            var min = array[0];
            for (var k = 1; k < array.length; k++) {
                if (array[k] < min) {
                    minIndex = k;
                    min = array[k];
                }
            }
            return minIndex;
        }
        //w przeciwnym wypadku wystawia swoja najwyzsza karte
        return maxIndex;
    }
    //jesli komputer odpowiada na karte gracza
    else{
        //sprawdza czy ma wysza karte od przeciwnika. Jesli ma kilka to wybiera najnizsza z nich
        var maxIndex = -1;
        var max = 1000000;
        var equalsIndex = -1;
        for (var i = 0; i < array.length; i++) {
            if (array[i] > playerPlayedCard && array[i]<max) {
                maxIndex = i;
                max = array[i];
            }
            else if(array[i]===playerPlayedCard)
                equalsIndex = i;
        }
        //jesli ma wyzsza karte to wystawia najnizsza z nich
        if(maxIndex!==-1)
            return maxIndex;
        //jesli nie, to wystawia rowna
        else if(equalsIndex!==-1)
            return equalsIndex;
        //jesli nie to wystawia swoja najnizsza karte
        var minIndex = 0;
        var min = array[0];
        for (var j = 1; j < array.length; j++) {
            if (array[j] < min) {
                minIndex = j;
                min = array[j];
            }
        }
        return minIndex;
    }
    };