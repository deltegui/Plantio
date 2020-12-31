<template>
  <div class="store-card" v-if="haveMessage">
    <div class="field danger-field">
      {{message}}
    </div>
    <span></span>
    <button v-on:click="messageOkClick" class="field fynd-btn">Ok</button>
  </div>
  <div class='store-card' v-else>
    <img :src="imgName"/>
    <h4>{{seedsType}} seeds</h4>
    <p>{{price}} <img src="credito.png"/> / unit</p>
    <NumericInput :max="max" :min="0" v-on:change="amountChange" />
    <p>Total {{total}} <img src="credito.png"/></p>
    <ButtonSpin text="Buy" v-on:click="buyClick"/>
  </div>
</template>

<script>
import ButtonSpin from './ButtonSpin.vue';
import NumericInput from './NumericInput.vue';
import storeService from '../logic/store.service';

export default {
  name: 'StoreCard',
  components: {
    NumericInput,
    ButtonSpin,
  },
  props: {
    price: {
      type: Number,
      required: true,
    },
    max: {
      type: Number,
      required: true,
    },
    seedsType: {
      type: String,
      required: true,
    },
  },
  data() {
    return {
      amount: 0,
      haveMessage: false,
      message: '',
    };
  },
  methods: {
    amountChange(value) {
      this.amount = value;
    },

    buyClick(stop) {
      storeService.buy({ item: this.seedsType, amount: this.amount })
        .then(() => {
          this.message = 'Done!';
        })
        .catch((err) => {
          this.message = err.msg;
        })
        .then(() => {
          this.haveMessage = true;
          stop();
          this.amount = 0;
        });
    },

    messageOkClick() {
      this.haveMessage = false;
    },
  },
  computed: {
    total() {
      return this.amount * this.price;
    },
    imgName() {
      return `bag_${this.seedsType.toLowerCase()}.png`;
    },
  },
};
</script>

<style scoped>
.store-card {
  display: grid;
  grid-template-columns: auto;
  grid-template-rows: 150px 40px 50px 40px 50px;

  background-color: #444444;

  justify-content: stretch;
  height: 400px;
  padding: 10px;

  border-style: solid;
  border-width: 1px;
  border-color: var(--bg-color);
  border-radius: 5px 5px 5px 5px;

  box-shadow: 2px 2px 1px black;
  box-sizing: border-box;

  -webkit-transition: border-color 0.5s;
  transition: border-color 0.5s;
}

.store-card:hover,
.store-card:focus-within {
  border-color: var(--front-color);
}

.store-card img {
  height: 150px;
  width: auto;
  justify-self: center;
}

.store-card span {
  justify-self: center;
}

.store-card h4 {
  justify-self: center;
}

.store-card p img {
  height: 15px;
}

.store-card p {
  justify-self: center;
}
</style>
