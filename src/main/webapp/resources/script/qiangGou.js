//存放主要交互逻辑js代码

var qiangGou = {
    //封装秒杀相关ajax的url
    URL : {
        now : function() {
            return '/qianggou/time/now';
        },
        exposer : function(qiangGouId) {
            return '/qianggou/'+qiangGouId+'/exposer';
        },
        execution : function(qiangGouId, md5) {
            return '/qianggou/'+qiangGouId+'/'+md5+'/execution';
        }
    },
    //处理秒杀逻辑
    handleQianggou : function(qiangGouId, node) {
        //获取秒杀地址，按制点击逻辑，执行秒杀
        node.hide()
            .html('<button class="btn btn-primary btn-lg" id="killBtn">开始抢购</button>'); // 按钮
        $.post(qiangGou.URL.exposer(qiangGouId), {}, function(result) {

            if (result && result['success']) {
                var exposer = result['data'];
                if (exposer['exposed']) {
                    //开启秒杀
                    //获取秒杀地址，
                    var md5 = exposer['md5'];
                    var qiangGouUrl = qiangGou.URL.execution(qiangGouId, md5);
                    console.log("qiangGouUrl:"+qiangGouUrl); //TODO

                    //绑定一次点击事件，防止多次点击
                    $('#killBtn').one('click', function(){
                        //执行秒杀请求
                        //1:先禁用按钮
                        $(this).addClass('disabled');
                        //2:发送秒杀请求
                        $.post(qiangGouUrl, {}, function(result){
                            if (result && result['success']) {
                                console.log('拿到秒杀结果'); //TODO
                                var killResult = result['data'];
                                var state = killResult['state'];
                                var stateInfo = killResult['stateInfo'];
                                //3：显示秒杀结果
                                node.html('<span class="label label-success">' + stateInfo + '</span>');
                            }
                        });
                    });
                    node.show();

                } else {
                    //未开启秒杀
                    var now = exposer['now'];
                    var start = exposer['start'];
                    var end = exposer['end'];
                    //重新计算计时逻辑
                    qiangGou.countdown(qiangGou, now, start, end);
                }

            } else {
                console.log('result:' + result);
            }

        });
    },
    //验证手机号
    validatePhone : function(phone) {
        if (phone && phone.length == 11 && !isNaN(phone)) {
            return true;
        } else {
            return false;
        }
    },
    // 计时交互
    countdown : function(qiangGouId, nowTime, startTime, endTime) {
        var qiangGouBox = $('#qiangGou-box');
        //秒杀结束
        if (nowTime > endTime) {

            qiangGouBox.html('抢购结束!');

            //秒杀未开始
        } else if (nowTime < startTime) {
            // 显示倒计时
            var killTime = new Date(startTime + 1000);
            qiangGouBox.countdown(killTime, function(event){
                //时间格式
                var format = event.strftime('抢购倒计时: %D天 %H时 %M分 %S秒');
                qiangGouBox.html(format);

                //倒计时完成开始秒杀
            }).on('finish.countdown', function(){

                qiangGou.handleQianggou(qiangGouId, qiangGouBox);
            });

            //秒杀开始
        } else {

            qiangGou.handleQianggou(qiangGouId, qiangGouBox);
        }
    },
    //详情页秒杀逻辑
    detail : {
        //详情页初始化
        init : function(params) {
            //手机验证和登录， 计时交互

            // 在cookie中查找手机号
            var killPhone = $.cookie('killPhone');
            console.log("killPhone" + killPhone);

            //验证手机号
            if (!qiangGou.validatePhone(killPhone)) {
                // 不通过
                console.log("手机号验证没通过"); //TODO
                //绑定手机号
                var killPhoneModal = $('#killPhoneModal');
                //显示弹出层
                killPhoneModal.modal({
                    show:     true,     //显示弹出层
                    backdrop: 'static', //禁止位置关闭
                    keyboard: false     //关闭键盘事件
                });
                $('#killPhoneBtn').click(function() {
                    var inputPhone = $('#killPhoneKey').val();
                    console.log("inputPhone: " + inputPhone); //TODO
                    if (qiangGou.validatePhone(inputPhone)) {
                        // 电话写入cookie
                        $.cookie('killPhone', inputPhone, {expires:7, path:'/qianggou'});
                        //刷新页面
                        window.location.reload();
                    } else {
                        $('#killPhoneMessage').hide().html('<label class="label label-danger">手机号错误！</label>').show(300);
                    }
                });
            } else {
                //已经登录
                //计时交互
                var qiangGouId = params['qiangGouId'];
                var startTime = params['startTime'];
                var endTime = params['endTime'];

                $.get(qiangGou.URL.now(), {}, function (result) {
                    console.log('进入了时间函数'); //TODO
                    if (result && result['success']) {
                        var nowTime = result['data'];
                        //时间判断
                        qiangGou.countdown(qiangGouId, nowTime, startTime, endTime);
                    } else {
                        console.log('result:'+result); //TODO
                    }
                });
            }

        }
    }


}