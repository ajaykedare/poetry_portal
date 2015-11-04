
//var poemApp = angular.module("poemApp", []);
poemApp.factory('MainService',['$http','$rootScope','$q', function(http,rootScope,q){
	var newService = new MainService(http,rootScope,q);
	return newService;
}]);


poemApp.controller("MainCtrl", function($scope, $http, $location, $q, MainService) {
	$scope.mainService=MainService;
	
	$scope.username="";
	$scope.signup_username="";
	$scope.password="";
	$scope.signup_password="";
	$scope.confirmpassword="";
	$scope.email="";
	$scope.mobile="";
	$scope.message="";
	

	if(!$scope.mainService.isInitialised)
	{
		//MainService.init();	
		MainService.isInitialised=true;

		MainService.loadpoems()
        .then(
            function (result) {
                // promise was fullfilled (regardless of outcome)
                // checks for information will be peformed here
                $scope.mainService.poemArray = result;
            },
            function (error) {
                // handle errors here
                console.log(error.statusText);
            }
        );		       
	}
	
	
	$scope.login = function(){

		var user={ 'username':$scope.username, 'password': $scope.password};
		user.mobile="0";
		user.email="a@b.com";
		$scope.message="";

		var result = $http({
			method : 'POST',
			url : 'login',
			data : JSON.stringify(user),			
		});
		result.success(function(data,status) {
		    if(!data.result)
		    {
		    	$location.path('/home');
		    	$scope.mainService.currentUser=data;
		    	$scope.mainService.isUserLoggedIn=true;
		    }
		    else
		    {		    		
		    	$scope.message="User does not exist, please try again !";
		    }
		  });
		result.error(function (data, status){
			console.log('Error Occured with status :'+status);
		});
	};

	$scope.signup = function(){
		var user={ 'username':$scope.signup_username, 'password': $scope.signup_password, 'mobile':$scope.mobile,'email':$scope.email};

		var result = $http({
			method : 'POST',
			url : 'signup',
			data : JSON.stringify(user),			
		});
		result.success(function(data,status) {
		    console.log(data);		    
		    if(data.result=="Success")
		    {
		    	alert('Signup successful '+ $scope.username + ' !\n Login to continue.');
		    	$location.path('/');
		    }
		    else
		    	{
		    		$scope.message="User does not exist, please try again !";
		    	}
		  });
		result.error(function (data, status){
			console.log('Error Occured with status :'+status);
		});
	};

	$scope.logout = function(){
		$location.path('/');
		$scope.mainService.currentUser=null;
		$scope.mainService.isUserLoggedIn=false;
	};
	
	$scope.upload = function(){
		$location.path('/');
		$scope.mainService.currentUser=null;
		$scope.mainService.isUserLoggedIn=false;
	};

	$scope.addlike = function(poem){
		poem.like+=1;
	};



	
});
		
