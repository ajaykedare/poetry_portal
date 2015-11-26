
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
	$scope.uerror="";
	$scope.perror="";
	$scope.message="";
	$scope.poemTextarea="";
	$scope.commentTextarea="";
	
	$scope.loadpoems = function(){
		MainService.loadpoems().then(
            function (result) {                
                $scope.mainService.poemArray = result;
            },
            function (error) {
                console.log(error.statusText);
            }
        );
	};

	if(!$scope.mainService.isInitialised)
	{
		MainService.isInitialised=true;
		$scope.loadpoems();
        if(!$scope.mainService.isUserLoggedIn){
        	$location.path('/');
        }
	}

	
	$scope.login = function(){
		$scope.uerror="";
		$scope.perror="";
		
		var user={ 'username':$scope.username, 'password': $scope.password};
		user.mobile="0";
		user.email="a@b.com";
		$scope.message="";
		if(($scope.username=='' && $scope.password=='')||(!$scope.username && !$scope.password)){
			$scope.uerror='Please enter Username';
			$scope.perror='Please enter Password';
			return;
		}
		if($scope.username=='' || !$scope.username){
			$scope.uerror='Please enter Username';			
			return;
		}
		if($scope.password=='' || !$scope.password){
			$scope.perror='Please enter Password';
			return;
		}

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
		    	$scope.message="Wrong username or password, please try again !";
		    }
		  });
		result.error(function (data, status){
			console.log('Error Occured with status :'+status);
		});
	};

	$scope.signup = function(){
		$scope.suerror="";
		$scope.sperror="";
		$scope.scperror="";
		$scope.merror="";
		var flag=1;
		var user={ 'username':$scope.signup_username, 'password': $scope.signup_password,'mobile':$scope.mobile,'email':$scope.email};
		
		if($scope.signup_username=='' || !$scope.signup_username){
			$scope.suerror='Please enter Username';			
			flag=0;
		}
		if($scope.signup_password=='' || !$scope.signup_password){
			$scope.sperror='Please enter Password';
			flag=0;
		}
		if($scope.confirmpassword=='' || !$scope.confirmpassword){
			$scope.scperror='Please re-enter Password';
			flag=0;
		}
		if($scope.confirmpassword!=$scope.signup_password){
			$scope.scperror='Confirm password not matching with above password';
			flag=0;
		}
	if($scope.mobile=='' || !$scope.mobile ){
			$scope.merror='Please enter your mobile number';
			flag=0;
		}
		if($scope.mobile.toString().length!=10 && !($scope.mobile=='' || !$scope.mobile) ){
			$scope.merror='Mobile number should be 10 digits';
			flag=0;
		}
		if($scope.email=='' || !$scope.email ){
			$scope.emerror='Please enter your email id';
			flag=0;
		}
		
		if(flag==0){
			return;
		}
		var result = $http({
			method : 'POST',
			url : 'signup',
			data : JSON.stringify(user),			
		});
		result.success(function(data,status) {
		    if(data.result=="Success")
		    {
		    	alert('Signup successful '+ $scope.username + ' !\n Login to continue.');
		    	$location.path('/');
		    }
		    else if(data.result=="UserExists")
		    	{
		    		$scope.message="Username already exists, please choose another !";
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
		console.log("Poem uploading : "+$scope.poemTextarea);
		var poem={};
		poem.username = $scope.mainService.currentUser.username;
		poem.data = $scope.poemTextarea;
		poem.likes = [];
		poem.date_ts = Date.now().toString(); //Take todays date
		poem.display_date = new Date().toString().substring(0,24);
		poem.comments=[];	

		var result = $http({
			method : 'POST',
			url : 'upload',
			data : JSON.stringify(poem),			
		});
		result.success(function(data,status) {
		    console.log(data);		    
		    if(data.result=="Success")
		    {
		    	$scope.loadpoems();
		    	alert('Upload successful ! \nThank you '+ $scope.mainService.currentUser.username + ' for uploading');
		    	$location.path('/home');
		    }
		    else
		    	{
		    		console.log('Error in uploading poem !');
		    		$scope.message="Error in uploading poem !";
		    	}
		  });
		result.error(function (data, status){
			console.log('Error in uploading poem !');		    		
		});
	};

	$scope.savepoemobject = function(){

		var poemArrayObj=$scope.mainService.poemArray;
		var result = $http({
			method : 'POST',
			url : 'save',
			data : angular.toJson(poemArrayObj),			
		});
		result.success(function(data,status) {
		    console.log(data);		    
		    if(data.result=="Success")
		    {
		    	console.log('Poem array saved successfully !');
		    }
		    else
		    {
		    		console.log('Error in saving poem array !');		    		
		    }
		});
		result.error(function (data, status){
			console.log('Error in saving poem array !');		    		
		});
	};

	$scope.addlike = function(poem){
		var username=$scope.mainService.currentUser.username;
		var index=poem.likes.indexOf(username);
		if(index==-1)
		{
			poem.likes.push(username);			
		}
		else
		{
			poem.likes.splice(index,1);
		}
		$scope.savepoemobject();
	};
	$scope.addCommentLike = function(comment){
		var username=$scope.mainService.currentUser.username;
		var index=comment.likes.indexOf(username);
		if(index==-1)
		{
			comment.likes.push(username);
			$scope.savepoemobject();
		}
		else
		{
			comment.likes.splice(index,1);
		}
		$scope.savepoemobject();
	};
	$scope.addComment = function(poem){
		var comment={};
		comment.username=$scope.mainService.currentUser.username;
		var commentData=document.getElementById('taid'+poem.date_ts).value;
		document.getElementById('taid'+poem.date_ts).value="";
		comment.text=commentData;
		comment.likes=[];
		comment.date_ts=Date.now().toString();
		comment.display_date=new Date().toString().substring(0,24);
		poem.comments.push(comment);
		$scope.savepoemobject();
	};
	
});
		
