/**
 * Created by Mike on 16/6/14.
 */

new Vue({
    el: 'body',
    compiled () {
        this.io = this.initConnection(this.initInfo, this.addMessage);
    },
    data: {
        io: {},
        user: {
            userId: null,
            name: '',
            gender: '',
            displayName: ''
        },

        chatSessions: [],

        activeChatSession: {
            userId: null,
            messages: []
        },

        onlineUsers: [],
        messageContent: '',
    },
    methods: {
        initConnection(initInfo, addMsg) {
            var conn = new WebSocket('ws://localhost:8080/chat');
            conn.onopen = function (event) {
                this.send('IR');
                this.status = 0;
            };
            conn.onmessage = function (event) {
                alert('build');
                var receiveMsg = event.data;
                alert(this.status);
                if (this.status == 0) {
                    initInfo(eval(receiveMsg));
                }
                if (this.status == 1) {
                    addMsg(eval(receiveMsg));
                }
            }
            conn.onerror = function (event) {
            }
            conn.onclose = function () {
            }
            return conn;
        },

        initInfo (data) {
            this.onlineUsers = data.userList;
            for (var user of this.onlineUsers) {
                user.genderClass = this.getGenderClass(user.gender);
            }
        },

        addUser (user) {
            user.genderClass = this.getGenderClass(user.gender);
            if (this.user.userId != user.userId) this.onlineUsers.push(user);
        },

        removeUser (userId) {
            var userIndex = this.onlineUsers.findIndex(u => u.userId == userId);
            if (userIndex != -1) this.onlineUsers.splice(userIndex, 1);

            var activeSession = this.chatSessions.find(cs => cs.userId == userId);
            if (activeSession) activeSession.online = false;
        },

        addMessage (msg) {
            console.log('Received new chat message: ')
            console.log(JSON.stringify(msg))

            // Check if we have an active chat session with the sender
            var activeSession = this.chatSessions.find(cs => cs.userId == msg.senderId)
            if (!activeSession) {
                this.addChatSession(msg.senderId, msg.senderDisplayName)
                activeSession = this.chatSessions[this.chatSessions.length - 1]
                if (!this.activeChatSession.userId) {
                    this.activeChatSession = activeSession
                }
            }

            if (this.activeChatSession.userId != msg.senderId) {
                activeSession.unread = true
                activeSession.messages.push(msg)
            } else {
                this.addMsgToActiveChat(msg)
            }
        },

        // Open chat session with user
        startChat (user) {

            var session = this.chatSessions.find(cs => cs.userId == user.userId)
            if (!session) {

                console.log('Starting new chat session with ' + user.displayName)
                this.addChatSession(user.userId, user.displayName)
                session = this.chatSessions[this.chatSessions.length - 1]
            }

            this.activeChatSession = session
        },

        /** Send chat message from the textarea to the recipient **/
        sendMessage (e, userId) {
            e.preventDefault()

            var msg = {
                content: this.messageContent,
                recipient: this.activeChatSession.userId,
                senderId: this.user.userId
            }

            this.addMsgToActiveChat(msg)
            this.messageContent = ''
            
            this.io.send(msg);
        },

        addChatSession (userId, displayName) {
            this.chatSessions.push({
                userId: userId,
                displayName: displayName,
                online: true,
                unread: false,
                messages: []
            })
        },

        setActiveChatSession (userId) {
            console.log('Setting active session to ' + userId);
            console.log(this.chatSessions[userId]);
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
        }
    },
    
    computed: {
        sessionMenuClass () {
            switch (this.chatSessions.length) {
                case 0:
                case 1:
                    return 'one'
                case 2:
                    return 'two'
                case 3:
                    return 'three'
                case 4:
                    return 'four'
                case 5:
                    return 'five'
                case 6:
                    return 'six'
            }
        }
    }
})