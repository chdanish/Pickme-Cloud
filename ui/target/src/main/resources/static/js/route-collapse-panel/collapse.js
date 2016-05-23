
    app.directive('collapse', function () {
        return {
            restrict: 'E',
            transclude: true,
            replace: true,
            scope: { title: '@' },
            controller: function ($scope, $element) {
                $scope.opened = false;
                return $scope.toggle = function () {
                    return $scope.opened = !$scope.opened;
                };
            },
            template: '  <div class="collapsible">\n  <header ng-click="toggle()">\n    <h4>{{title}}</h4>\n  </header>\n  <section ng-transclude ng-class="{opened: opened}"></section>\n</div>'
        };
    });
