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
    var suma = Number(0);
    if(array.length>7 || array2.length>7)
        return smarter(computerLeftCards,playerLeftCards,pcTurn,playerPlayedCard);
    else{
        var wynik = recursion(array2,array,pcTurn,playerPlayedCard,suma);
        return wynik[1];
    }
    };
    
var recursion = function(playerArray, computerArray, pcTurn, playedCard, sum){
    if(playerArray.length===1 && computerArray.length===1){
        if (playerArray[0]>computerArray[0])
            return [sum-2, 0];
        else if (playerArray[0]===computerArray[0])
            return [sum, 0];
        return [sum+2, 0];
    }
    else{
        var array = new Array(computerArray.length);
        var played;
        //przypadek gry dogrywamy do gracza - liczymy maksimum naszych punktow
        if(computerArray.length>playerArray.length){
            var max;
            var maxIndex = 0;
            played = computerArray[0];
            if(playedCard>played)
                var wynik = recursion(playerArray,spliceNoMutate(computerArray,0),true,played,sum-2);
            else if(playedCard===played)
                var wynik = recursion(playerArray,spliceNoMutate(computerArray,0),true,played,sum);
            else
                var wynik = recursion(playerArray,spliceNoMutate(computerArray,0),true,played,sum+2);
            array[0] = wynik[0];
            max = array[0];
            for(var i=1;i<computerArray.length;i++){
                played = computerArray[i];
                if(playedCard>played)
                    var wynik = recursion(playerArray,spliceNoMutate(computerArray,i),true,played,sum-2);
                else if(playedCard===played)
                    var wynik = recursion(playerArray,spliceNoMutate(computerArray,i),true,played,sum);
                else
                    var wynik = recursion(playerArray,spliceNoMutate(computerArray,i),true,played,sum+2);
                array[i] = wynik[0];
                if(array[i]>max){
                    max = array[i];
                    maxIndex = i;
                }
            }
            return [max, maxIndex];
        }
        //przypadek gry gracz dogrywa do nas - liczy minimum naszych punktów
        else if(computerArray.length<playerArray.length){
            var min;
            var minIndex = 0;
            played = playerArray[0];
            if(playedCard>played)
                var wynik = recursion(spliceNoMutate(playerArray,0),computerArray,false,played,sum+2);
            else if(playedCard===played)
                var wynik = recursion(spliceNoMutate(playerArray,0),computerArray,false,played,sum);
            else
                var wynik = recursion(spliceNoMutate(playerArray,0),computerArray,false,played,sum-2);
            array[0] = wynik[0];
            min = array[0];
            for(var i=1;i<computerArray.length;i++){
                played = computerArray[i];
                if(playedCard>played)
                    var wynik = recursion(spliceNoMutate(playerArray,i),computerArray,false,played,sum+2);
                else if(playedCard===played)
                    var wynik = recursion(spliceNoMutate(playerArray,i),computerArray,false,played,sum);
                else
                    var wynik = recursion(spliceNoMutate(playerArray,i),computerArray,false,played,sum-2);
                array[i] = wynik[0];
                if(array[i]<min){
                    min = array[i];
                    minIndex = i;
                }
            }
            return [min, minIndex];
        }
        //przypadek gdy komputer zaczyna swoja ture
        else if(pcTurn === true){
            var max;
            var maxIndex = 0;
            played = computerArray[0];
            var wynik = recursion(playerArray,spliceNoMutate(computerArray,0),false,played,sum);
            array[0] = wynik[0];
            max = array[0];
            for(var i=1;i<computerArray.length;i++){
                played = computerArray[i];
                var wynik = recursion(playerArray,spliceNoMutate(computerArray,i),false,played,sum);
                array[i] = wynik[0];
                if(array[i]>max){
                    max = array[i];
                    maxIndex = i;
                }
            }
            return [max, maxIndex];
        }
        //przypadek gdy gracz zaczyna swoja ture
        else{
            var min;
            var minIndex = 0;
            played = playerArray[0];
            var wynik = recursion(spliceNoMutate(playerArray,0),computerArray,true,played,sum);
            array[0] = wynik[0];
            min = array[0];
            for(var i=1;i<computerArray.length;i++){
                played = computerArray[i];
                var wynik = recursion(spliceNoMutate(playerArray,i),computerArray,true,played,sum);
                array[i] = wynik[0];
                if(array[i]<min){
                    min = array[i];
                    minIndex = i;
                }
            }
            return [min, minIndex];
        }
    }
};

function spliceNoMutate(myArray,indexToRemove) {
    return myArray.slice(0,indexToRemove).concat(myArray.slice(indexToRemove+1));
};

var smarter = function(computerLeftCards,playerLeftCards,pcTurn,playerPlayedCard) {
    realJsString = computerLeftCards + "";
    array = realJsString.split("|");
    for(var i=0;i<array.length;i++)
        array[i]=Number(array[i]);
    realJsString2 = playerLeftCards + "";
    array2 = realJsString2.split("|");
    for(var i=0;i<array2.length;i++)
        array2[i]=Number(array2[i]);
    playerPlayedCard = Number(playerPlayedCard);
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