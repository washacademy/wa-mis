module.exports = function(grunt) {

  grunt.initConfig({
    pkg: grunt.file.readJSON('package.json'),

      uglify: {
           dist: {
             files: {
               'dist/app1.js': [ 'dist/app1.js' ],
               'dist/app2.js': [ 'dist/app2.js' ]
             },
             options: {
               mangle: false
             }
           }
         },



    concat: {
      options: {
        separator: ';\n\n'
      },
      dist1: {
        src: ['bower_components/angular/angular.min.js','bower_components/angularjs-captcha/dist/angularjs-captcha.min.js','scripts/app.js','scripts/factories/userForm.js','scripts/controllers/main.js', 'scripts/global/urls.js', 'scripts/controllers/login.js','scripts/controllers/reports.js', 'scripts/controllers/bulkUser.js', 'scripts/controllers/changePassword.js',
          'scripts/controllers/createUser.js','scripts/controllers/downloads.js','scripts/controllers/editUser.js','scripts/controllers/forgotPassword.js', 'scripts/controllers/profile.js',
          'scripts/controllers/staticPages.js','scripts/controllers/userManagement.js','scripts/controllers/userManual.js', 'scripts/directives/pagination.js','scripts/controllers/userTable.js'],
        dest: 'dist/app1.js'
      },
      dist2: {
          src: ['scripts/factories/auth.js', 'scripts/factories/exportuiGrid.js', 'scripts/factories/userTable.js', 'bower_components/cryptojslib/rollups/aes.js', 'bower_components/angular-cryptography/mdo-angular-cryptography.js'],
          dest: 'dist/app2.js'
      },

      css : {
          src: [ 'bower_components/bootstrap/dist/css/bootstrap.min.css','bower_components/angular-material/angular-material.min.css',
                'bower_components/angular-ui-grid/ui-grid.min.css', 'styles/style.css' ],
          dest: 'dist/app.css'

      }

    },

  });



  grunt.loadNpmTasks('grunt-contrib-concat');
  grunt.loadNpmTasks('grunt-contrib-uglify');
  grunt.loadNpmTasks('grunt-html2js');
  grunt.loadNpmTasks('grunt-contrib-watch');
  grunt.loadNpmTasks('grunt-bower-task');


  grunt.registerTask('dev', [ 'bower', 'watch:dev' ]);
  grunt.registerTask('test', [ 'bower']);
  grunt.registerTask('minified', [ 'bower', 'watch:min' ]);
  grunt.registerTask('package', [ 'concat:dist1', 'concat:dist2', 'uglify:dist']);

};