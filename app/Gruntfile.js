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
        src: ['bower_components/angular/angular.min.js','scripts/app.js','scripts/factories/userForm.js','scripts/controllers/main.js', 'scripts/global/urls.js', 'scripts/controllers/login.js','scripts/controllers/reports.js', 'scripts/controllers/bulkUser.js', 'scripts/controllers/changePassword.js',
        'scripts/controllers/createUser.js','scripts/controllers/downloads.js','scripts/controllers/editUser.js','scripts/controllers/faq.js',
        'scripts/controllers/feedbackForm.js','scripts/controllers/forgotPassword.js', 'scripts/controllers/profile.js', 'scripts/controllers/contactUs.js', 'scripts/controllers/contactUsResponse.js',
        'scripts/controllers/staticPages.js','scripts/controllers/userManagement.js','scripts/controllers/userManual.js', 'scripts/directives/pagination.js','scripts/controllers/userTable.js'],
        dest: 'dist/app1.js'
      },
      dist2: {
          src: ['scripts/factories/auth.js', 'scripts/factories/exportuiGrid.js', 'scripts/factories/userTable.js', 'bower_components/cryptojslib/rollups/aes.js', 'bower_components/angular-cryptography/mdo-angular-cryptography.js'],
          dest: 'dist/app2.js'
      },
//      vendor1: {
//          src: ['bower_components/angular/angular.min.js',
//          'bower_components/angular-animate/angular-animate.min.js',
//          'bower_components/angular-ui-carousel/dist/ui-carousel.min.js'],
//          dest: 'dist/vendor1.js'
//      },
//      vendor2: {
//            src: ['bower_components/angular-ui-router/release/angular-ui-router.min.js',
//            'bower_components/angular-bootstrap/ui-bootstrap-tpls.min.js','bower_components/angular-messages/angular-messages.min.js',
//            'bower_components/angular-ui-grid/ui-grid.min.js', 'bower_components/ng-device-detector/ng-device-detector.min.js',
//            'bower_components/angular-ui-validate/dist/validate.min.js','bower_components/angular-aria/angular-aria.min.js','bower_components/re-tree/re-tree.min.js',
//            'bower_components/ngstorage/ngStorage.min.js','bower_components/ng-file-upload/ng-file-upload-shim.min.js',
//            'bower_components/angular-cryptography/mdo-angular-cryptography.js', 'bower_components/ng-file-upload/ng-file-upload.min.js',
//             'bower_components/pdfmake/build/pdfmake.min.js','bower_components/angular-idle-service/dist/angular-idle-service.js',
//             'bower_components/pdfmake/build/vfs_fonts.js', 'bower_components/cryptojslib/rollups/aes.js'],
//            dest: 'dist/vendor2.js'
//       },

      css : {
          src: [ 'bower_components/bootstrap/dist/css/bootstrap.min.css','bower_components/angular-material/angular-material.min.css',
                'bower_components/angular-ui-grid/ui-grid.min.css', 'styles/style.css' ],
          dest: 'dist/app.css'

      }

    },


//
//    watch: {
//      dev: {
//        files: [ 'Gruntfile.js','scripts/**/*.js','/scripts/*.js'],
//        tasks: [ 'concat:dist',],
//        options: {
//          atBegin: true
//        }
//      },
//      min: {
//        files: [ 'Gruntfile.js','scripts/**/*.js','/scripts/*.js'],
//        tasks: [ 'concat:dist','uglify:dist' ],
//        options: {
//          atBegin: true
//        }
//      }
//    }

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

  // Loading of tasks and registering tasks will be written here
};