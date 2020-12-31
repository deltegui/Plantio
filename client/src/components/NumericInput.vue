<template>
    <input type="number" class="field" :value="current" :max="max" :min="min" v-on:change="onChange"/>
</template>

<script>
export default {
  name: 'NumericInput',
  props: {
    min: {
      type: Number,
      required: true,
    },
    max: {
      type: Number,
      required: true,
    },
  },
  data() {
    return {
      current: this.min,
    };
  },
  methods: {
    onChange(evt) {
      const value = parseInt(evt.target.value, 10);
      if (isNaN(value)) {
        return;
      }
      if (value < this.min || value > this.max) {
        return;
      }
      this.current = value;
      this.$emit('change', this.current);
    },
  },
};
</script>
