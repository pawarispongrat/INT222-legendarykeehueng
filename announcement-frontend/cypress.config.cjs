const { defineConfig } = require("cypress");

module.exports = defineConfig({
  e2e: {
   specPattern: "cypress/sas-r2-sprint2-v1.0/**/*.{cy,spec}.{js,jsx,ts,tsx}",
   baseUrl: "http://localhost:5173",
   experimentalSessionAndOrigin: true
  },
});
