<template>
  <button :class="buttonClass">
    {{ text }}
  </button>
</template>

<script>
export default {
  name: 'ActionButton',
  props: {
    text: {
      type: String,
      required: true
    },
    type: {
      type: String,
      required: false,
      default: 'primary',
      validator(value) {
        return ['primary', 'secondary'].includes(value)
      }
    }
  },
  computed: {
    buttonClass() {
      return {
        [this.type]: true
      }
    }
  }
}
</script>

<style>
button {
  @apply rounded px-4 py-1.5 font-medium;
}

.primary {
  @apply border-0 bg-mcl-orange text-white hover:shadow-mcl-orange;
}

.secondary {
  @apply bg-transparent text-mcl-orange;
}

.secondary:hover {
  @apply border-0 bg-mcl-pale-orange shadow-mcl-pale-orange text-white;
}
</style>
