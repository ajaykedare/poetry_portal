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