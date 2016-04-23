    (function() {
    	'use strict';

    	var module = angular.module('translateServices');

    	module.factory('SecurityRepository', function($http, Constant) {
    		return {
    			authenticate: function(credentials) {
    				return $http.post(Constant.api + '/authenticate', credentials).then(function(data) {
    					return data;
    				});
    			},
    			getUser: function() {
    				return $http.get(Constant.apiSecure + '/user').then(function(user) {
    					return user;
    				});
    			},
    			changePassword: function(id, username, password) {
    				return $http.post(Constant.apiSecure + '/user/change-password', {
    					id: id,
    					username: username,
    					password: password
    				});
    			}
    		};
    	});

    	module.factory('UserRepository', function($resource, Constant) {
    		return $resource(Constant.apiSecure + '/user', {
    			id: '@id'
    		}, {
    			updateUser: {
    				method: 'PUT'
    			}
    		});
    	});

    	module.factory('LangRepository', function($http, Constant) {
    		return {
    			load: function() {
    				return $http.get(Constant.api + '/langs').then(function(langs) {
    					return langs.data;
    				});
    			},
    			save: function(lang) {
    				return $http.post(Constant.apiSecure + '/langs', lang).then(function(lang) {
    					return lang.data;
    				});
    			},
    			deleteLang: function(lang) {
    				return $http.delete(Constant.apiSecure + '/langs/' + lang.id);
    			}
    		};
    	});

    	module.factory('WordRepository', function($resource, Constant, $http) {
    		return {
    			load: function() {
    				return $http.get(Constant.api + '/word').then(function(words) {
    					return words.data;
    				});
    			},
    			save: function(word) {
    				return $http.post(Constant.apiSecure + '/word', word).then(function(word) {
    					return word.data;
    				});
    			},
    			deleteWord: function(word) {
    				return $http.delete(Constant.apiSecure + '/word/delete/' + word.id);
    			},
    			updateWord: function(word) {
    				return $http.put(Constant.apiSecure + '/word/update/' + word.id, word).then(function(word) {
    					return word.data;
    				});
    			},
    			loadByCategoryId: function(categoryId)  {
    				return $http.get(Constant.api + '/word/category/' + categoryId).then(function(words) {
    					return words.data;
    				});
    			}
    		};
    	});

    	module.factory('CategoryRepository', function($http, Constant) {
    		return {
    			load: function() {
    				return $http.get(Constant.api + '/category').then(function(categories) {
    					return categories.data;
    				});
    			},
    			save: function(category) {
    				return $http.post(Constant.apiSecure + '/category', category).then(function(category) {
    					return category.data;
    				});
    			},
    			deleteCategory: function(category) {
    				return $http.delete(Constant.apiSecure + '/category/' + category.id);
    			},
    			updateCategory: function(category) {
    				return $http.put(Constant.apiSecure + '/category/' + category.id, category).then(function(category) {
    					return category.data;
    				});
    			},
    			loadCategoryAffect: function() {
    				return $http.get(Constant.api + '/category-words').then(function(categories) {
    					return categories.data;
    				});
    			}
    		};


    	});

    	module.factory('LevelRepository', function($http, Constant) {
    		return {
    			load: function() {
    				return $http.get(Constant.api + '/level').then(function(levels) {
    					return levels.data;
    				});
    			},
    			save: function(level) {
    				return $http.post(Constant.apiSecure + '/level', level).then(function(level) {
    					return level.data;
    				});
    			},
    			updateLevel: function(level) {
    				return $http.put(Constant.apiSecure + '/level/' + level.id, level).then(function(level) {
    					return level.data;
    				});
    			},
    			deleteLevel: function(level) {
    				return $http.delete(Constant.apiSecure + '/level/' + level.id);
    			}
    		};
    	});

    	module.factory('ScoreRepository', function($resource, Constant) {
    		return $resource(Constant.api + '/score', {}, {
    			load: {
    				method: 'GET',
    				isArray: true
    			}
    		});
    	});

    	module.factory('SettingRepository', function($resource, Constant) {
    		return $resource(Constant.api + '/setting', {}, {
    			load: {
    				method: 'GET',
    				isArray: false
    			}
    		});
    	});

    	module.factory('MessageRepository', function($http, Constant) {
    		return {
    			getLanguages: function() {
    				return $http.get(Constant.api + '/translate')
    					.then(function(response) {
    						return response.data;
    					});
    			},
    			getMessages: function(key) {
    				return $http.get(Constant.api + '/messages/' + key)
    					.then(function(response) {
    						$http.defaults.headers.common.I18n = key.split('_')[0];
    						return response.data;
    					});
    			}

    		};

    	});

    }());
