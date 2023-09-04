import {createStore} from 'vuex'

const MEMBER = 'member';

export default createStore({
    state: {
        // || {} 避免空指针异常
        member: window.SessionStorage.get(MEMBER) || {},
    },
    getters: {},
    mutations: {
        setMember(state, _member) {
            state.member = _member;
            window.SessionStorage.set(MEMBER, _member);
        }
    },
    actions: {

    },
    modules: {}
})
