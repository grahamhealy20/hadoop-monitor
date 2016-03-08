////////// Controller for sidebar //////////
angular.module('admin').controller('SidebarCtrl', function ($scope, $location) {
	//Check for current path for active element
	$scope.isCurrentPath = function (path) {
		return $location.path() == path;
	}
});