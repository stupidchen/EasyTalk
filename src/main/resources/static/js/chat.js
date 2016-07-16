/**
 * Created by Mike on 16/6/14.
 */
function getTime(date) {
    if(date == null) {
        date = new Date();
    }
    var y = date.getFullYear();
    var M = date.getMonth() + 1;
    var d = date.getDate();
    var h = date.getHours();
    var m = date.getMinutes();
    var s = date.getSeconds();
    var html = y + "-";
    if(M < 10)
    {
        html += "0";
    }
    html += M + "-";

    if(d < 10)
    {
        html += "0";
    }
    html += d + " ";
    if(h < 10)
    {
        html += "0";
    }
    html += h + ":";
    if(m < 10)
    {
        html += "0";
    }
    html += m + ":";
    if(s < 10)
    {
        html += "0";
    }
    html += s;
    return html;
}

var chat = {
    el: '#chat',
    created () {
        this.io = this.initConnection(this.initInfo, this.initUserList, this.addUser, this.removeUser, this.addMessage);
    },
    
    data: {
        io: {},

        user: {
            userId: null,
            username: '',
            gender: '',
        },

        chatSessions: [],
        /*
        userId: userId,
        username: username,
        online: true,
        unread: false,
        messages: []
         */

        activeChatSession: {
            userId: null,
            messages: []
        },

        onlineUsers: [],
        message: '',
    },
    methods: {
        initConnection(initInfo, initUserList, addUser, removeUser, addMessage) {
            getTokenCookie();
            var conn = new WebSocket('ws://localhost:8080/chat');
            conn.onopen = function (event) {
                this.send('UI:' + token);
                this.status = 0;
            };
            conn.onmessage = function (event) {
                var receiveMsg = event.data;
                var msg = receiveMsg.substr(3, receiveMsg.length);
                var obj = eval('(' + msg + ')');
                //alert(receiveMsg);
                if (receiveMsg.indexOf('UI') == 0) {
                    initInfo(obj);
                    this.send("OI");
                }
                if (receiveMsg.indexOf('OI') == 0) {
                    initUserList(obj); 
                }
                if (receiveMsg.indexOf('AU') == 0) {
                    addUser(obj);
                }
                if (receiveMsg.indexOf('MR') == 0) {
                    addMessage(obj);
                }
                if (receiveMsg.indexOf('RU') == 0) {
                    removeUser(obj);
                }
            }
            conn.onerror = function (event) {
            }
            conn.onclose = function () {
            }
            return conn;
        },

        initInfo (info) {
            this.user.gender = info.gender;
            this.user.userId = info.userId;
            this.user.username = info.username;
            this.user.genderClass = this.getGenderClass(this.user.gender);
        },
        
        initUserList (userList) {
            this.onlineUsers = userList;
            for (var user of this.onlineUsers) {
                user.genderClass = this.getGenderClass(user.gender);
            }
        },

        addUser (user) {
            user.genderClass = this.getGenderClass(user.gender);
            if (this.user.userId != user.userId) this.onlineUsers.push(user);
        },

        //TOFIX add the event driver
        removeUser (userId) {
            var userIndex = this.onlineUsers.findIndex(u => u.userId == userId);
            if (userIndex != -1) this.onlineUsers.splice(userIndex, 1);

            var activeSession = this.chatSessions.find(cs => cs.userId == userId);
            if (activeSession) activeSession.online = false;
        },

        //TOFIX add the event driver
        addMessage (msg) {
            // Check if we have an active chat session with the sender
            var activeSession = this.chatSessions.find(cs => cs.userId == msg.fromUserId)
            if (!activeSession) {
                this.addChatSession(msg.fromUserId, msg.fromUserId);
                activeSession = this.chatSessions[this.chatSessions.length - 1];
                if (!this.activeChatSession.userId) {
                    this.activeChatSession = activeSession;
                }
            }

            if (this.activeChatSession.userId != msg.fromUserId) {
                //alert(msg.fromUserId);
                activeSession.unread = true;
                activeSession.messages.push(msg);
            } else {
                this.addMsgToActiveChat(msg);
            }
        },

        // Open chat session with user
        startChat (user) {
            var session = this.chatSessions.find(cs => cs.userId == user.userId);
            if (!session) {
                this.addChatSession(user.userId, user.username);
                session = this.chatSessions[this.chatSessions.length - 1];
            }

            this.activeChatSession = session;
        },

        /** Send chat message from the textarea to the recipient **/
        sendMessage (e, userId) {
            e.preventDefault();

            var timeStr = getTime(new Date());
            var msg = {
                toUserId: this.activeChatSession.userId,
                fromUserId: this.user.userId,
                message: this.message,
                sendTime: timeStr
            };

            this.addMsgToActiveChat(msg);
            
            this.io.send('MS:' + JSON.stringify(msg));
            this.message = '';
        },

        addChatSession (userId, username) {
            this.chatSessions.push({
                userId: userId,
                username: username,
                online: true,
                unread: false,
                messages: []
            });
        },

        setActiveChatSession (userId) {
            this.activeChatSession = this.chatSessions.find(cs => cs.userId == userId);
            this.activeChatSession.userId = userId;
            this.activeChatSession.unread = false;
            this.autoScroll();
        },

        closeChatSession (userId, e) {
            e.stopPropagation();

            var sessionIndex = this.chatSessions.findIndex(cs => cs.userId == userId);
            if (this.activeChatSession.userId == userId) {
                this.activeChatSession = {
                    userId: null,
                    messages: []
                }
            }
            if (sessionIndex != -1) this.chatSessions.splice(sessionIndex, 1);
            this.activeChatSession.userId = null;
        },

        addMsgToActiveChat (msg) {
            this.activeChatSession.messages.push(msg);
            this.autoScroll();
        },

        autoScroll () {
            // Not that pretty fix to allow the DOM to update before we autoscroll
            setTimeout(() => {
                this.$els.chatMsgWindow.scrollTop = this.$els.chatMsgWindowContent.scrollHeight
            }, 100);
        },

        getGenderClass (gender) {
            switch (gender) {
                case 'Male':
                    return 'man';
                case 'Female':
                    return 'woman';
                case 'Other':
                    return 'other gender';
            }
        },

    },
    
    computed: {
        sessionMenuClass () {
            var obj = {
                one: false,
                two: false,
                three: false,
                four: false,
                five: false,
                six: false,
                seven: false
            }
            switch (this.chatSessions.length) {
                case 0:
                case 1:
                    obj.one = true; return obj;
                case 2:
                    obj.two = true; return obj;
                case 3:
                    obj.three = true; return obj;
                case 4:
                    obj.four = true; return obj;
                case 5:
                    obj.five = true; return obj;
                case 6:
                    obj.six = true; return obj;
                case 7:
                    obj.seven = true; return obj;
            }
            return obj;
        }
    }
}

var app = new Vue(chat);