<template>
<div class="spinner">
  <div class="spinner-item"></div>
  <div class="spinner-item"></div>
  <div class="spinner-item"></div>
  <div class="spinner-item"></div>
  <div class="spinner-item"></div>
</div>
</template>

<script>
export default {
  name: 'Spinner',
};
</script>

<style scoped>

.spinner {
  --size: 25px;
  --clr: white;
  --clr1: var(--clr);
  --clr2: var(--clr);
  --clr3: var(--clr);
  --clr4: var(--clr);
  --clr5: var(--clr);

  --animation-duration: 1000ms;
  width: var(--size);
  height: var(--size);
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 4px;
}

.spinner-item {
  height: 40%;
  background-color: var(--clr-spinner);
  width: calc(var(--size) / 13);
  animation: loading var(--animation-duration) ease-in-out infinite;
}

@keyframes loading {
  25% {
    transform: scaleY(2);
  }

  50% {
    transform: scaleY(1);
  }
}

.spinner-item:nth-child(1) {
  --clr-spinner: var(--clr1);
}

.spinner-item:nth-child(2) {
  --clr-spinner: var(--clr3);
  animation-delay: calc(var(--animation-duration) / 10);
}

.spinner-item:nth-child(3) {
  --clr-spinner: var(--clr5);
  animation-delay: calc(var(--animation-duration) / 10 * 2);
}

.spinner-item:nth-child(4) {
  --clr-spinner: var(--clr4);
  animation-delay: calc(var(--animation-duration) / 10 * 3);
}

.spinner-item:nth-child(5) {
  --clr-spinner: var(--clr2);
  animation-delay: calc(var(--animation-duration) / 10 * 4);
}
</style>
