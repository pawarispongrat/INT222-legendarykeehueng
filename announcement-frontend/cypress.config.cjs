const { defineConfig } = require("cypress");

module.exports = defineConfig({
  e2e: {
   specPattern: "cypress/sas-r2-sprint1-v1/**/*.{cy,spec}.{js,jsx,ts,tsx}",
   baseUrl: "http://intproj22.sit.kmutt.ac.th/kp1",
   experimentalSessionAndOrigin: true
  },
});
