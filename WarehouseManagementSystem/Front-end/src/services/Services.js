import axios from 'axios';

const BASE_URL = 'http://your-backend-api-url';

const YourService = {
  fetchData: async () => {
    try {
      const response = await axios.get(`${BASE_URL}/data`);
      return response.data;
    } catch (error) {
      console.error('Error fetching data:', error);
      throw error;
    }
  },
  // Add more service methods as needed
};

export default YourService;
