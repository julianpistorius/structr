/*
 *  Copyright (C) 2010-2013 Axel Morgner
 *
 *  This file is part of structr <http://structr.org>.
 *
 *  structr is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 *
 *  structr is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with structr.  If not, see <http://www.gnu.org/licenses/>.
 */

var s = require('../setup');

var testName = '008_create_folder';
var heading = "Create Folder", sections = [];
var desc = "This animation shows how to create a new folder."
var numberOfTests = 3;

s.startRecording(window, casper, testName);

casper.test.begin(testName, numberOfTests, function(test) {

    casper.start(s.url);
    
    casper.thenEvaluate(function() {
        window.localStorage.clear();
    }, {});
    
    sections.push('Enter username and password.');

    casper.then(function() {
        s.animatedType(this, '#usernameField', false, 'admin');
    });

    casper.then(function() {
        s.animatedType(this, '#passwordField', false, 'admin');
    });

    sections.push('Click on the login button.');

    casper.then(function() {
        s.mousePointer(casper, { left: 600, top: 400 });
        s.moveMousePointerTo(casper, '#loginButton');
    });

    casper.then(function() {
        this.click('#loginButton');
    });

    casper.waitForSelector('#errorText', function() {

        test.assertEval(function() { return !($('#errorText').text() === 'Wrong username or password!'); });

        test.assertEval(function() { return $('#pages').is(':visible'); });

    });

    sections.push('Click on the "Files" menu entry.');
    
    casper.then(function() {
        s.moveMousePointerTo(casper, '#files_');
    });

    casper.then(function() {
        this.click('#files_');
    });

    casper.wait(1000);

    sections.push('Click the "Add Folder" icon.');
    
    casper.then(function() {
        s.moveMousePointerTo(casper, '.add_folder_icon');
    });

    casper.then(function() {
        this.click('.add_folder_icon');
    });

    casper.wait(1000);

    sections.push('A new folder with a random name has been created in the folders area.');

    casper.then(function() {
        test.assertEval(function() {
            return $('#folders .node.folder').size() === 1;
        });
    });

    casper.then(function() {
        
        s.animateHtml(testName, heading, sections);

        test.done();
        this.exit();
        
    });

    casper.run();

});
