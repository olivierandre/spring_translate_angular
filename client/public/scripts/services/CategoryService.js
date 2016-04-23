(function() {
    'use strict';
    var module = angular.module('translateServices');

    module.service('CategoryService', function(CategoryRepository) {

        var self = this;

        self.loadCategories = function() {
            var promise = CategoryRepository.load();

            promise.then(function(categories) {
                self.categories = categories;
            });

            return promise;
        };

        self.loadCategoriesAffect = function() {
            var promise = CategoryRepository.loadCategoryAffect();

            promise.then(function(categories) {
                self.categories = categories;
            });

            return promise;
        };

        self.updateCategory = function(category) {
            return CategoryRepository.updateCategory(category).then(function(updateCategory) {
                var idx = self.categories.indexOf(category);
                self.categories[idx] = updateCategory;

                return self.categories;
            });
        };


        self.saveCategory = function(category) {
            return CategoryRepository.save(category).then(function(newCategory) {
                self.categories.push(newCategory);
                return self.categories;
            });
        };

        self.deleteCategory = function(category) {
            var id = category.id;

            return CategoryRepository.deleteCategory({id: id}).then(function() {
                var idx = self.categories.indexOf(category);
                self.categories.splice(idx, 1);
                return self.categories;
            });
        };

        self.getCategoryName = function(id) {
            var labels = {
                fr: '',
                en: ''
            };
            angular.forEach(self.categories, function(category) {
                if (category.id === id) {
                    labels = category.labels;
                }
            });
            return labels;
        };


    });
}());
