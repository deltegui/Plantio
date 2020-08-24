<template>
<div id="text-console">
> Hello maverick! <br/>
> The time is 13:44 and there is 22 degrees outside <br/>
> _
</div>
</template>

<script>
export default {
};
</script>

<style scoped>
@font-face {
  font-family: consoles;
  src: url(/console.ttf);
}

#text-console {
  box-sizing: border-box;

  background-color: #272727;
  color: #E5E5E5;

  width: 430px;
  height: 90vh;
  margin-top: 5vh;
  padding: 5px;
  border-radius: 10px;

  float: left;
  overflow: scroll;

  font-family: consoles;
}
</style>
