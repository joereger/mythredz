

function cdtime(container, secondsuntilnextexercise){
    if (!document.getElementById || !document.getElementById(container)) return
    this.container=document.getElementById(container)
    this.timesup=false
    this.secondsuntilnextexercise = secondsuntilnextexercise;
    this.updateTime()
}

cdtime.prototype.updateTime=function(){
    var thisobj=this
    this.secondsuntilnextexercise = this.secondsuntilnextexercise - 1;
    setTimeout(function(){thisobj.updateTime()}, 1000) //update time every second
}

cdtime.prototype.displaycountdown=function(baseunit, functionref){
    this.baseunit=baseunit
    this.formatresults=functionref
    this.showresults()
}

cdtime.prototype.showresults=function(){
    var thisobj=this
    var timediff = this.secondsuntilnextexercise;
    if (timediff<0){ //if time is up
        this.timesup=true;
        this.container.innerHTML=this.formatresults();
        NewWindow('/timetoworkout.jsp','Time to Workout!','0','0','yes');
        alert("PingFit says it's time to exercise!");
        return ;
    }
    var oneMinute=60 //minute unit in seconds
    var oneHour=60*60 //hour unit in seconds
    var oneDay=60*60*24 //day unit in seconds
    var dayfield=Math.floor(timediff/oneDay)
    var hourfield=Math.floor((timediff-dayfield*oneDay)/oneHour)
    var minutefield=Math.floor((timediff-dayfield*oneDay-hourfield*oneHour)/oneMinute)
    var secondfield=Math.floor((timediff-dayfield*oneDay-hourfield*oneHour-minutefield*oneMinute))
    if (this.baseunit=="hours"){ //if base unit is hours, set "hourfield" to be topmost level
        hourfield=dayfield*24+hourfield
        dayfield="n/a"
    } else if (this.baseunit=="minutes"){ //if base unit is minutes, set "minutefield" to be topmost level
        minutefield=dayfield*24*60+hourfield*60+minutefield
        dayfield=hourfield="n/a"
    } else if (this.baseunit=="seconds"){ //if base unit is seconds, set "secondfield" to be topmost level
        var secondfield=timediff
        dayfield=hourfield=minutefield="n/a"
    }
    this.container.innerHTML=this.formatresults(dayfield, hourfield, minutefield, secondfield)
    setTimeout(function(){thisobj.showresults()}, 1000) //update results every second
}

/////CUSTOM FORMAT OUTPUT FUNCTIONS BELOW//////////////////////////////

//Create your own custom format function to pass into cdtime.displaycountdown()
//Use arguments[0] to access "Days" left
//Use arguments[1] to access "Hours" left
//Use arguments[2] to access "Minutes" left
//Use arguments[3] to access "Seconds" left

//The values of these arguments may change depending on the "baseunit" parameter of cdtime.displaycountdown()
//For example, if "baseunit" is set to "hours", arguments[0] becomes meaningless and contains "n/a"
//For example, if "baseunit" is set to "minutes", arguments[0] and arguments[1] become meaningless etc


function formatresults(){
    if (this.timesup==false){//if target date/time not yet met
        var displaystring=arguments[2]+"<sup>min</sup> "+arguments[3]+"<sup>sec</sup>"
    } else{ //else if target date/time met
        var displaystring="Now!"
    }
    return displaystring
}

function NewWindow(mypage, myname, w, h, scroll) {
    if (w>0) {
    //Do nothing... we're using the input values
    } else if (window.screen) {
        w = window.screen.availWidth - 50;
    } else {
        w = 800
    }
    if (h>0) {
        //Do nothing... we're using the input values
    } else if (window.screen) {
        h = window.screen.availHeight - 100;
    } else {
        h = 600
    }
    var percent = 90;
    var winprops = 'height='+ h +',width='+ w +',top=25,left=25,scrollbars='+ scroll +',resizable';
    var win = window.open(mypage, myname, winprops)
    if (parseInt(navigator.appVersion) >= 4) { win.window.focus(); }
}


