const { defineConfig } = require("cypress");

module.exports = defineConfig({
  e2e: {
   specPattern: "cypress/sas-r2-sprint2-v1.0/**/*.{cy,spec}.{js,jsx,ts,tsx}",
   baseUrl: "https://intproj22.sit.kmutt.ac.th/kp1",
   experimentalSessionAndOrigin: true,
   experimentalRunAllSpecs: true
  },
});
