<template>
  <div ref="container" class="three-container mx-auto mb-10 max-w-screen-xl">
    <canvas ref="glCanvas" class="glcanvas left-0 top-0 h-full w-full"></canvas>
  </div>
</template>

<script>
import * as THREE from 'three'
import { OrbitControls } from 'three/examples/jsm/controls/OrbitControls'
import { GLTFLoader } from 'three/addons/loaders/GLTFLoader.js'

export default {
  name: 'ThreeJsAnimation',
  data() {
    return {
      ctrlPressed: false
    }
  },
  mounted() {
    this.initThree()
    window.addEventListener('resize', this.onWindowResize)
    window.addEventListener('keydown', this.onCtrlDown)
    window.addEventListener('keyup', this.onCtrlUp)
    this.loop()
  },
  beforeUnmount() {
    window.removeEventListener('resize', this.onWindowResize)
    window.removeEventListener('keydown', this.onCtrlDown)
    window.removeEventListener('keyup', this.onCtrlUp)
  },
  methods: {
    initThree() {
      this.scene = new THREE.Scene()

      const loader = new GLTFLoader()
      loader.load(
        '/animation/porsche_911_gt2_rs/scene.gltf',
        (gltf) => {
          this.scene.add(gltf.scene)
        },
        undefined,
        (error) => {
          console.error(error)
        }
      )

      const light = new THREE.PointLight(0xffffff, 400, 100)
      light.position.set(0, 10, 10)
      this.scene.add(light)

      const width = this.$refs.container.clientWidth
      const height = this.$refs.container.clientHeight * 0.8

      this.camera = new THREE.PerspectiveCamera(45, width / height, 0.1, 100)
      this.camera.position.z = 15
      this.camera.position.y = 5
      this.scene.add(this.camera)

      const canvas = this.$refs.glCanvas
      this.renderer = new THREE.WebGLRenderer({ canvas })
      this.renderer.setSize(width, height)
      this.renderer.setPixelRatio(2)
      this.renderer.setClearColor(0x121212)

      this.controls = new OrbitControls(this.camera, this.renderer.domElement)
      this.controls.enableZoom = false
      this.controls.enableDamping = true
      this.controls.autoRotate = true
    },
    loop() {
      this.controls.update()
      this.renderer.render(this.scene, this.camera)
      requestAnimationFrame(this.loop)
    },
    updateSize() {
      const width = this.$refs.container.clientWidth
      const height = this.$refs.container.clientHeight

      this.camera.aspect = width / height
      this.camera.updateProjectionMatrix()
      this.renderer.setSize(width, height)
    },
    updateControls() {
      this.controls.enableZoom = this.ctrlPressed
    },
    onCtrlDown(event) {
      if (event.altKey) {
        this.ctrlPressed = true
        this.updateControls()
      }
    },
    onCtrlUp(event) {
      if (!event.altKey) {
        this.ctrlPressed = false
        this.updateControls()
      }
    },
    onWindowResize() {
      this.updateSize()
    }
  }
}
</script>
