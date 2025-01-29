<template>
  <router-link
    :to="url"
    class="block px-2 py-2 text-white underline-offset-8 hover:underline md:bg-transparent md:p-0"
  >
    <slot />
  </router-link>
</template>

<script>
export default {
  name: 'NavBarItem',
  props: {
    url: {
      type: String,
      required: false,
      default: '#'
    }
  }
}
</script>
