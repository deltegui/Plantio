export default {
  nowInUnix() {
    const now = new Date();
    return now.getTime() / 1000;
  },
};
