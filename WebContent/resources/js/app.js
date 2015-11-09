var poemApp = angular.module('poemApp',['ngRoute','ngAnimate', 'ui.bootstrap']);
//var poemApp = angular.module("poemApp", []);

poemApp.config(['$routeProvider', function($routeProvider){	
	$routeProvider
	.when('/',{
		templateUrl:'resources/templates/indexloginhome.html',
		controller:'MainCtrl'
	})
	.when('/signup',{
		templateUrl:'resources/templates/signup.html',
		controller:'MainCtrl'
	})
	.when('/home',{
		templateUrl:'resources/templates/home.html',
		controller:'MainCtrl'
	})
	.when('/upload',{
		templateUrl:'resources/templates/upload.html',
		controller:'MainCtrl'
	})
	.otherwise({
		redirectTo : '/'
	});

} ]);


poemApp.directive('poemDirective', function(){
    return function(scope, element, attr){
     //   element.bind('click', function(){ 
        	element.html().replace(/\n|\r\n|\r/g, "<br>");                  
       // });
    };
});

