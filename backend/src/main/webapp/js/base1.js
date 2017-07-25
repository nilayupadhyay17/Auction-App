var myapp = myapp || {};
myapp.auction = myapp.auction || {};
var responsearr = [];
var auctionarr = [];
 var dauction = [];
 var wauction = [];
var tauction = [];
var adetail =[];
myapp.auction.init = function(apiRoot) {
//alert("init");

    var apisToLoad;
    var cb = function() {
    //   myapp.auction.auctionstatuslist();
       // myapp.auction.auctionTime();
    }
       var cb1 = function() {
        myapp.auction.auctionlist();
        }
        var cb2 = function() {
        myapp.auction.showWinner();
        }
    myapp.auction.auctionTime = function() {
            //    alert("enable button");
            gapi.client.auctionTimeApi.list().execute(function(resp) {
                //console.log(resp);
                if (!resp.code) {
                    resp.items = resp.items || [];
                    var response = resp.items;
                    $.each(response, function(key, value) {
                        temp = [value.id,value.auctionName,value.dateTime];
                        responsearr.push(temp);
                    });
                  //  console.log(responsearr);
                }
                myapp.auction.print(responsearr);
            });

            $.each(responsearr, function(key, value) {
               // console.log(value);
            });
     };

    myapp.auction.startauction = function(time,name) {
        //    alert("enable button");
        gapi.client.auctionTimeApi.insert({
            'totalTime': time,
             'name': name
        }).execute(function(resp) {
            if (!resp.code) {
//                myapp.auction.print(resp);
            }
        });
    };
     var select = document.querySelector("#selectAuction");
     var sidenav = document.querySelector("#mySidenav");

function showAlert(event) {
         //alert("hi");
          $('#main').show();
          //console.log(this.text);
        var table = $('#myTable').DataTable();
         $('#main1').show();
         $('#s1').show();
         $('#s2').show();
         $('#s1').removeClass('toggleDisplay');
         $('#s2').removeClass('toggleDisplay');
           //console.log(this.text);
         var table1 = $('#myTable1').DataTable();
         table.destroy();
         table1.destroy();

         auctionarr = [];
         tempdata = [];
         winauctionarr = [];
         wintempdata = [];
          //console.log(temp);
         temp  = dauction[this.text];
         wtemp  = wauction[this.text];
         var atime = adetail[this.text];
         $( "#s1" ).empty();

          $('#s1').append('<h1>'+this.text+'</h1>');

         //console.log("adetail !!!!"+atime);
         for(var key in temp){
            if(typeof temp[key].userid != 'undefined'){
            //console.log("userid");
            //console.log(temp[key].userid);
            tempdata = [temp[key].userid,temp[key].type,temp[key].bid];
         // console.log("tdata "+tempdata);
          auctionarr.push(tempdata);
          }
         //console.log(temp[key]);
         }
         for(var key in wtemp){
                   wintempdata = [wtemp[key].name,wtemp[key].Driver,wtemp[key].DBid,wtemp[key].PBid,wtemp[key].Pass,wtemp[key].price,wtemp[key].dlocation,wtemp[key].plocation,wtemp[key].pdestination];
                  // console.log("tdata "+tempdata);
                   winauctionarr.push(wintempdata);
                  //console.log(temp[key]);
                  }
        // console.log("win "+winauctionarr);
         myapp.auction.print(auctionarr,winauctionarr);
         //myapp.auction.printWinner(winauctionarr);
         //var table = $('table.table1').DataTable();
        // table.Destroy();

           //alert(this.value );
         }

    myapp.auction.auctionlist = function() {
      //   alert("enable button");

        gapi.client.auctionApi.list().execute(function(resp) {
            //console.log(resp);
            if (!resp.code) {
                resp.items = resp.items || [];
                var response = resp.items;
                var i = 0;
                $.each(response, function(key, value) {
                       if(typeof value.endtime != 'undefined'){
                       var now = new Date().getTime();
                      // console.log("end time"+value.endtime);
                       var distance = value.endtime - now;
                       if(distance >0){
                        console.log("distance "+distance);
                        var days = Math.floor(distance / (1000 * 60 * 60 * 24));
                         var hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
                         var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
                         var seconds = Math.floor((distance % (1000 * 60)) / 1000);
                          //console.log("m "+minutes+" seconds "+seconds);
                          var timeLeft = minutes + "m " + seconds + "s  Left";}
                          else{
                            var timeLeft = "is ended";
                           }
                         //  console.log(timeLeft);
                           if(!(value.auctionName in adetail)){
                               console.log("not in"+value.auctionName);
                               console.log(adetail);
                               adetail[value.auctionName]=timeLeft;
                           }
                           console.log(adetail);
                          // $('#s1').innerHTML=timeLeft;
                            }
                    //temp = [value.auctionName,value.userID,value.type,value.bid];
                    if (value.auctionName in dauction){
                   // console.log("end time"+value.endtime);

                    dauction[value.auctionName].push({"name":value.auctionName,"userid":value.userID,"type":value.type,"bid":value.bid});
                    }
                    else {
                    dauction[value.auctionName] =[{"name":value.auctionName,"userid":value.userID,"type":value.type,"bid":value.bid}];
                    var aTag = document.createElement('a');
                   // aTag.setAttribute('href',"yourlink.htm");
                    aTag.innerHTML = value.auctionName;
                    aTag.href = "#"
                    aTag.onclick = showAlert;
                    aTag.className += "auctionName";
                    sidenav.appendChild(aTag);
                   //  select.options[select.options.length] = new Option(value.auctionName,value.auctionName);
                    }
                    i++;
                    //auctionarr.push(temp);
                });

                for(key in dauction){
                // console.log(dauction[key]);
                }
              console.log(dauction);
            }
        });
    };
     myapp.auction.auctionstatuslist = function() {
          //   alert("enable button");

            gapi.client.auctionTimeApi.list().execute(function(resp) {
              //  console.log(resp);
                if (!resp.code) {
                    resp.items = resp.items || [];
                    var response = resp.items;
                    var i = 0;
                    $.each(response, function(key, value) {
                    console.log("calling");
                        //temp = [value.auctionName,value.userID,value.type,value.bid];
                        if (value.auctionName in tauction){
                        }
                        else {
                         var now = new Date().getTime();
                                                	console.log("time"+now);
                                                    var distance = value.endTime - now;
                                                    if(distance >0){
                                                    console.log("distance "+distance);
                                                    var days = Math.floor(distance / (1000 * 60 * 60 * 24));
                                                    var hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
                                                    var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
                                                    var seconds = Math.floor((distance % (1000 * 60)) / 1000);
                                                    console.log("m "+minutes+" seconds "+seconds);
                                                    var timeLeft = minutes + "m " + seconds + "s ";}
                                                    else{
                                                    var timeLeft = "Auction Finished";
                                                    }
                        tauction[value.auctionName] =[{"name":value.auctionName,"time":timeLeft}];
                        }
                    }   );
                     console.log(tauction);
                }
            });
        };
    $(document).ready(function(){
        $('.auctionName').attr("onClick", "javascript:alert('clicked'); return false;");
    });

     myapp.auction.auctionprint = function() {
       myapp.auction.print();
     }
    var callbackGCM = function() {
    }
    gapi.client.load('auctionTimeApi', 'v1', cb, apiRoot);
    gapi.client.load('auctionApi', 'v1', cb1, apiRoot);
    gapi.client.load('gCMMessagesApi', 'v1', callbackGCM, apiRoot);
    gapi.client.load('auctionWinnerApi', 'v1', cb2, apiRoot);
};
myapp.auction.stopauction = function() {
    gapi.client.gCMMessagesApi.stopauction().execute(function(resp) {
        if (!resp.code) {

        }
    })
};
myapp.auction.showWinner = function() {
    gapi.client.auctionWinnerApi.getAuctionWinner().execute(function(resp) {
        if (!resp.code) {
         resp.items = resp.items || [];
         var response1 = resp.items;
        // console.log("daata"+response1);
          $.each(response1, function(key, value) {
                           console.log("an"+value.price);
                          if (value.auctionName in wauction){
                           // console.log("if");
                           // console.log(wauction[value.auctionName]);
                            wauction[value.auctionName].push({"name":value.auctionName,"Driver":value.userD,"DBid":value.bidD,"PBid":value.bidP,"Pass":value.userP,"dlocation":value.dlocation,"plocation":value.plocation,"pdestination":value.pdestination,"price":value.price});
                       } else {
                            //console.log("else");
                           // console.log(value.auctionName);
                           // console.log(wauction[value.auctionName]);
                            wauction[value.auctionName] =[{"name":value.auctionName,"Driver":value.userD,"DBid":value.bidD,"PBid":value.bidP,"Pass":value.userP,"dlocation":value.dlocation,"plocation":value.plocation,"pdestination":value.pdestination,"price":value.price}];
                       }
                             //auctionarr.push(temp);
                         });
                          for(key in wauction){
                                        //  console.log(wauction[key]);
                                         }
          console.log("Wauction");
        }
    })
          console.log(wauction);
}
//var stopauction = document.querySelector("#stopauction");
myapp.auction.print = function(auctiondata,winnerdata) {
console.log(auctiondata);
console.log("winner data");
console.log(winnerdata);
$('th.toggleDisplay').removeClass('toggleDisplay');

table = $('#myTable').DataTable( {
        data:auctiondata,
         "paging":   false,
          "ordering": false,
          "info":     false
    } );
table1 = $('#myTable1').DataTable( {
        data:winnerdata,
         "paging":   false,
          "ordering": false,
          "info":     false
    } );
    $(document).ready(function() {
        $('#myTable1 tbody').on('click', 'tr', function () {
            var data = table1.row( this ).data();
        swal({
          title: "More Details",
          text: "<b>Driver Location </b>"+data[6]+"<br><b>Passenger Location </b>"+data[7]+"<br><b>Passenger Destination </b>"+data[8],
          html: true
        });
    } );
});
myapp.auction.printWinner = function(auctiondata) {
//console.log(auctiondata);
$('th.toggleDisplay').removeClass('toggleDisplay');

table1 = $('#myTable1').DataTable( {
        data:auctiondata,
         "paging":   false,
          "ordering": false,
          "info":     false
    } );

};
}