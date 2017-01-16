
Ext.onReady(function () {
            //初始化标签中的Ext:Qtip属性。
            Ext.QuickTips.init();
            Ext.form.Field.prototype.msgTarget = 'side';

            //创建文本上传域
            var exteditor = new Ext.form.HtmlEditor({
                fieldLabel: '员工描述'
            });
            //整合KE编辑器
            var keeditor = new Ext.form.TextArea({
				renderTo:"tree1",
                id: 'keeditor',
                fieldLabel: '员工描述',
                width: 700,
                height: 200
            });

            //表单
            var form = new Ext.form.FormPanel({
                frame: true,
                title: '表单标题',
                style: 'margin:10px',
                items: [exteditor, keeditor],
                listeners: {
                    'render': function () {
                        KE.show({
                            id: 'keeditor',
                            imageUploadJson: '/App_Ashx/Upload.ashx'
                        });
                    }
                }
            });
			
			var form2 = new Ext.form.FormPanel({
                frame: true,
                title: '表单标题',
                style: 'margin:10px',
                items: [exteditor]
            });
            //窗体
            var win = new Ext.Window({
                title: '窗口',
                width: 900,
                height: 700,
                resizable: true,
                modal: true,
                closable: true,
                maximizable: true,
                minimizable: true,
                buttonAlign: 'center',
                items: {
						title:'Chat Groups',
						border:false,
						html:'<div id="tree1" style="overflow:auto;width:100%;height:100%" ></div>',
						//iconCls:'nav'
					}
            });
	
            win.show();
        });
