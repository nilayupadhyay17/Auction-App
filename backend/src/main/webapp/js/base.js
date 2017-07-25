/**
 * @fileoverview
 * Provides methods for the Hello Endpoints sample UI and interaction with the
 * Hello Endpoints API.
 */


 /**
 * Initializes the application.
 * @param {string} apiRoot Root of the API's path.
 */
 function start() {
   gapi.client.init({
     'apiKey': 'AIzaSyB6f5x4NNMvXN_HnOx1rffKRObTiUcMurQ',
     'discoveryDocs': ['https://coral-core-175.appspot.com/_ah/api/discovery/v1/apis/auctionTimeApi/v1/rest'],
//     'clientId':'9160635882-n7olfcc6h8m1f96780er2l8h5orkn0da.apps.googleusercontent.com',
//     'scope': 'profile',
     // clientId and scope are optional if auth is not required.
   });
 }
 gapi.load('client', start);
var auction = auction || {};
  auction.appengine = auction.appengine || {};
  auction.appengine.samples = auction.appengine.samples || {};
  auction.appengine.samples.hello = auction.appengine.samples.hello || {};
//
//auction.appengine.samples.init = function(apiRoot) {
//  // Loads the OAuth and helloworld APIs asynchronously, and triggers login
//  // when they have completed.
//
//    var callback = function() {
//    auction.appengine.samples.hello.enableButtons();
//    }
////  alert("auction");
//   gapi.client.load('gCMMessagesApi', 'v1', callback, apiRoot);
//};

  var getBidding = document.querySelector('#getBidding');
  getBidding.addEventListener('click', function(e) {
    auction.appengine.samples.hello.insert();
  });
/*
auction.appengine.samples.hello.enableButtons = function() {
  var getBidding = document.querySelector('#getBidding');
  getBidding.addEventListener('click', function(e) {
    auction.appengine.samples.hello.insert();
  });
};
*/
auction.appengine.samples.hello.insert = function() {
    /*$('auctionStatus').append("<h1>Auction Started</h1>");*/
  gapi.client.auctionTimeApi.insert().execute();
//       alert('auction started');
//       document.querySelector('#auctionStatus').appendChild(element);

//      function(resp) {
//
//      });
}
