'use strict';

describe('Controller: HeaderController', function () {

  // load the controller's module
  beforeEach(module('translateApp'));

  var HeaderController,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    HeaderController = $controller('HeaderController', {
      $scope: scope
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(scope.todos.length).toBe(0);
  });

  it('should add items to the list', function () {
    scope.todos.todo = 'Test 1';
    scope.addTodo();
    expect(scope.todos.length).toBe(1);
  });

  it('shoud remove a item to the list', function() {
      scope.todos.todo = 'Test 1';
      scope.addTodo();
      scope.removeTodo(0);
      expect(scope.todos.length).toBe(0);
  });
});
