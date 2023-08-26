const { defineConfig } = require("cypress");

module.exports = defineConfig({
  e2e: {
   specPattern: "cypress/sas-r2-sprint1-v1/**/*.{cy,spec}.{js,jsx,ts,tsx}",
   baseUrl: "http://localhost:5173",
   experimentalSessionAndOrigin: true
  },
});
