<template>
  <button v-on:click="doWork" class="field fynd-btn">
    <div class="centered-spinner" v-if="working">
    <Spinner/>
    </div>
    <div v-else>{{text}}</div>
  </button>
</template>

<script>
import Spinner from './Spinner.vue';

export default {
  name: 'ButtonSpinner',
  components: {
    Spinner,
  },
  props: {
    text: {
      type: String,
      required: true,
    },
  },
  data() {
    return {
      working: false,
    };
  },
  methods: {
    doWork() {
      this.working = true;
      this.$emit('click', () => {
        this.working = false;
      });
    },
  },
};
</script>

<style scoped>
.centered-spinner {
  margin-left: auto;
  margin-right: auto;
}
</style>
