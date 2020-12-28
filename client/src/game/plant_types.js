export default [
  {
    key: 'wheat',
    description: `The easiest plant to start. Works well in almost
    any environment.`,
    image: 'wheat.png',
    temperature: {
      min: 16,
      max: 28,
    },
    humidityDeath: 20,
    humidityGrowth: 30,
  },
  {
    key: 'cactus',
    description: `The best plant for dry environments!!`,
    image: 'cactus.png',
    temperature: {
      min: 25,
      max: 50,
    },
    humidityDeath: 10,
    humidityGrowth: 23,
  },
  {
    key: 'lavender',
    description: `A plant that resist cold`,
    image: 'lavender.png',
    temperature: {
      min: -10,
      max: 17,
    },
    humidityDeath: 40,
    humidityGrowth: 55,
  },
];
