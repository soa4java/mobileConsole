/*
Copyright (c) 2003-2011, CKSource - Frederico Knabben. All rights reserved.
For licensing, see LICENSE.html or http://ckeditor.com/license
*/

CKEDITOR.editorConfig = function( config )
{
	config.language = 'zh-cn'; 
	config.width = '76%'; 
	config.height = 200; 
	config.skin = 'office2003'; 
	config.toolbar_Full = [
	            ['Bold','Italic','Underline','Strike','-','Subscript','Superscript'],
	            ['NumberedList','BulletedList','-','Outdent','Indent','Blockquote'],
	            ['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],
	            ['Link','Unlink','Anchor'],
	            //['Image','Flash','Table','HorizontalRule','Smiley','SpecialChar','PageBreak'],
	             '/',
	            ['Styles','Format','Font','FontSize'],
	            ['TextColor','BGColor']
	 ]; 

};
