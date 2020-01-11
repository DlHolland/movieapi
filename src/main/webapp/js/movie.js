/**
 * 
 */
var movieapp= angular.module('movieapp', ['ngRoute']);

movieapp.config(function($routeProvider) {
	$routeProvider
	.when('/search', {
		templateUrl: 'search.html', 
	})
	.when('/create', {
		templateUrl: 'create.html',
		controller : 'movieCreateController'
	})
	.when('/stacks', {
		templateUrl: 'stacks.html'
	})
	.when('/resume', {
		templateUrl: 'resume.html'
	})
	.when('/link1', {
		templateUrl: 'link1.html'
	})
	.when('/link2', {
		templateUrl: 'link2.html'
	})
	.when('/welcome', {
		templateUrl: 'welcome.html'
	})
	.when('/hobbies', {
		templateUrl: 'hobbies.html'
	})
	.when('/fancysearch', {
		templateUrl: 'fancysearch.html',
		controller : 'movieFancyController'
	})	
	.when('/notify', {
		templateUrl: 'notify.html',
		controller : 'movieNotifyController'
	})	
	.otherwise({
		templateUrl: 'welcome.html'
	});
});

movieapp.controller('movieCreateController', function($scope, $http) {
	
	$scope.postMovie = function() {
		$scope.jsonObject = angular.toJson($scope.newMovie);
		console.log('new movie: ' + $scope.jsonObject)
		$http.post("/movieapi/rest/v1/movie", $scope.jsonObject)
		.then(function(response) {					
					$scope.createStatus = 'successful insert of new movie';
					$scope.successfulInsert = true;
				}, function error(response) {
					$scope.createStatus = 'insert error, ' + response.data.message;
				}
		);
		
	}
	

	
	$scope.clearMovie = function() {
		$scope.createStatus = 'Enter new movie information';
		$scope.successfulInsert = false;		
		$scope.newMovie = {
				title : '',
				description : '',
				genre : '',
				releaseYear : ''
				
		};
	}
	

});

movieapp.controller('movieFancyController', function($scope, $http) {
	$scope.getFancySearch = function() {
		console.log('fancy movie search');
		console.log('fancy search: ' + angular.toJson($scope.fancysearch, false));
		
		var config = {params: $scope.fancysearch}
		
		$http.get("/movieapi/rest/v1/movie/fancysearch", config)
			.then(function(response) {
				$scope.searchResults = response.data;
			}, function error(response) {
				console.log('error, return status: ' + response.status);
			});
		
	};
	
	
	$scope.clearFancySearch = function() {
		$scope.fancysearch.english = false;
		$scope.fancysearch.french = false;
		$scope.fancysearch.spanish = false;
		$scope.fancysearch.german = false;
		
		$scope.fancysearch.media = '';
		
		$scope.fancysearch.startdate = '';
		$scope.fancysearch.enddate = '';
		
		$scope.searchResults = '';
	};
})

movieapp.controller('movieNotifyController', function($scope, $http) {
	$scope.emailCustomers = function() {
		var email = {
				emailSubject : $scope.emailSubject,
				emailText : $scope.emailText				
		};
		
		$scope.jsonEmailObject = angular.toJson(email, false);
		console.log('email customers: ' + $scope.jsonEmailObject);				
			
		$http.post("/movieapi/rest/v1/movie/email", $scope.jsonEmailObject)
		.then(
				function success(response) {
					$scope.emailStatus = "success. press 'Clear' to enter new email";								
				},
				function error(response) {
					console.log('error sending email, status: ' + response.status);
					$scope.emailStatus = "error. press 'Clear' to try again";						
				}				
		);			
	};
	
	$scope.emailCustomersClear = function() {
		console.log('email customers clear');
		$scope.emailSubject = "";
		$scope.emailText = "";
		$scope.emailStatus = "";
	}
	

	$scope.textCustomers = function() {		
		console.log('text customer');
		var text = {
				textNumber : $scope.textNumber,
				textContent : $scope.textContent				
		};
		
		$scope.jsonTextObject = angular.toJson(text, false);
		console.log('text customers: ' + $scope.jsonTextObject);				
			
		$http.post("/movieapi/rest/v1/movie/text", $scope.jsonTextObject)
		.then(
				function success(response) {
					$scope.textStatus = "success. press 'Clear' to enter new text";								
				},
				function error(response) {
					console.log('error sending text, status: ' + response.status);
					$scope.textStatus = "error. press 'Clear' to try again";						
				}				
		);
	};
	
	$scope.textCustomersClear = function() {		
		$scope.textNumber = '';
		$scope.textContent = '';
		$scope.textStatus = '';
	};

})



movieapp.controller('moviecontroller', function($scope, $http) {
	
	
			
	 $scope.showSearch = true;
	 $scope.minReleaseYear = 1890;
	 $scope.maxReleaseYear = 2021;
	 
	 $scope.getMovies = function() {
		 console.log('getMovies() called')
		 $http.get("/movieapi/rest/v1/movie")
		 .then(function(response) {
			 $scope.movies = response.data;
			 console.log('number of movies: ' + $scope.movies.length)
		 }, function(response) {
			 console.log('Error HTTP GET movies: ' + response.status)
		 });

	 } 
	 $scope.selectMovie = function(movieToUpdate) {
		 $scope.movieToUpdate = angular.copy(movieToUpdate);
		 console.log('updateMovies() called' + angular.toJson(movieToUpdate));
		 $scope.showSearch = false;
		 $scope.updateStatus = "";
		 $scope.isUpdateButtonDisabled = false;
		 $scope.isDeleteButtonDisabled = false;

	 }
	 $scope.genreValues = ['Drama', 'action', 'Sci-fi', 'Thriller'];
	 
	 $scope.updateMovie = function() {
		 $scope.jsonObject = angular.toJson($scope.movieToUpdate);
		 console.log('updateMovie() called' + $scope.jsonObject);
		 
		 $http.put("/movieapi/rest/v1/movie", $scope.jsonObject)
		 .then(function(response) {
			 $scope.updateStatus = 'update successful'
			 console.log('number of movies: ' + $scope.movies.length)
		 }, function(response) {
			 $scope.updateStatus = 'update error: ' + response.data.message + ', status: ' + response.status;
		 });
	 }
	 $scope.deleteMovie = function() {
		 $scope.jsonObject = angular.toJson($scope.movieToUpdate);
		 console.log('deleteMovie() called' + $scope.jsonObject);
		 
		 $http.delete("/movieapi/rest/v1/movie" + "/" + $scope.movieToUpdate.movieId)
		 .then(function(response) {
			 $scope.updateStatus = 'delete successful'
			 $scope.isUpdateButtonDisabled = true;
			 $scope.isDeleteButtonDisabled = true;
			 console.log('number of movies: ' + $scope.movies.length)
		 }, function(response) {
			 $scope.updateStatus = 'delete error: ' + response.data.message + ', status: ' + response.status;
		 });
	 }
	 $scope.cancelMovie = function() {
		 console.log('cancelMovie() called');
		 $scope.showSearch = true;
		 $scope.getMovies();
	 }
	 
	 
	 

});