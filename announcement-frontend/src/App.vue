<script  setup>

</script>

<template>
  <div class="flex flex-col items-center font bg-base-200 bg-cover h-screen w-screen " >
    <router-view></router-view>
  </div>
</template>

<style>

@font-face {
  font-family: "Kanit-Regular";
  src: url(./assets/font/Kanit-Regular.ttf);
}
@font-face {
  font-family: "Kanit-Light";
  src: url(./assets/font/Kanit-Light.ttf);
}
.font {
  font-family: "Kanit-Regular", serif;
  letter-spacing: 0.05rem;
}

</style>
