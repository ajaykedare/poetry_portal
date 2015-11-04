function MainService (httpService,rootScope,q)
{
	this.httpService = httpService;
	this.rootScope=rootScope;
	this.q=q;
	this.currentUser={};
	this.isUserLoggedIn=false;
	console.log('Service Initialised !');
}


MainService.prototype.loadpoems = function(){
	var deferred = this.q.defer();
	return this.httpService({
			method : 'POST',
			url : 'loadpoems',
		})
            .then(function (response) {
                // promise is fulfilled
                deferred.resolve(response.data);
                // promise is returned
                console.log('Poems retrieved !');	
                return deferred.promise;
            }, function (response) {
                // the following line rejects the promise 
                deferred.reject(response);
                console.log('Error in poems retrieved !');
                // promise is returned
                return deferred.promise;
            })
        ;

	/*return this.httpService({
			method : 'POST',
			url : 'loadpoems',
		}).success(function(data,status) {
			   this.poemArray=data;
			    console.log('Poems retrieved :'+JSON.stringify(data));		    		    
		  }).error(function (data, status){
			this.poemArray=[];
			console.log('Error in loading poems. Status :'+status);	
		});*/  
		/*return this.httpService({
			method : 'POST',
			url : 'loadpoems',
		});*/
};

MainService.prototype.init = function(){
	
	/*this.poemArray = [
    { 
    	"username":"Ajay",
        "data": "Best Friends The title we chose But what does it mean to be best friends? You should see each other every day?..",
        "likes": 10,
        "date":"02/11/2015"
    },
    {  
    	"username":"Ajay2",
    	"data": "2 Best Friends The title we chose But what does it mean to be best friends? You should see each other every day?..",
        "likes": 50,
        "date":"03/10/2015"
    }
];*/
    var result = this.httpService({
			method : 'POST',
			url : 'loadpoems',
		});
		result.success(function(data,status) {
			this.rootScope.$apply(function () { 
			    this.poemArray=data;
			    console.log('Poems retrieved :'+JSON.stringify(data));		    		    
			});
		  });
		result.error(function (data, status){
			this.poemArray=[];
			console.log('Error in loading poems. Status :'+status);	
		});                        

};