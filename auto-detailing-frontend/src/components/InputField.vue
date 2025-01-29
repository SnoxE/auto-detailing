<template>
  <input :value="modelValue" @input="$emit('update:modelValue', $event.target.value)" />
</template>

<script>
export default {
  name: 'InputField',
  props: {
    modelValue: {
      type: String,
      default: ''
    }
  },
  emits: { 'update:modelValue': '' }
}
</script>
