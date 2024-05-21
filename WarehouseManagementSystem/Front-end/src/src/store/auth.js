const state = {
    user: null,
    isAuthenticated: false
  };
  
  const getters = {
    getUser: state => state.user,
    isAuthenticated: state => state.isAuthenticated
  };
  
  const actions = {
    login({ commit }, userData) {
      // Simulate login process
      // Replace with actual login API call
      // Upon successful login, set user data and isAuthenticated to true
      commit('setUser', userData);
      commit('setIsAuthenticated', true);
    },
    logout({ commit }) {
      // Simulate logout process
      // Replace with actual logout API call
      // Upon successful logout, clear user data and set isAuthenticated to false
      commit('setUser', null);
      commit('setIsAuthenticated', false);
    }
  };
  
  const mutations = {
    setUser(state, userData) {
      state.user = userData;
    },
    setIsAuthenticated(state, isAuthenticated) {
      state.isAuthenticated = isAuthenticated;
    }
  };
  
  export default {
    state,
    getters,
    actions,
    mutations
  };
  