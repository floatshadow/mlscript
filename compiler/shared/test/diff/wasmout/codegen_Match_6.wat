(module 
  (func $log (import "console" "log") (param i32))
  (memory $memory 1)
  (global (mut i32) i32.const 0) 
  (export "main" (func $main))
  (func $main (local $e i32)(local $tmp2 i32)(local $at1 i32)(local $ad1 i32)(local $y i32)(local $m1 i32)(local $i i32)(local $ah i32)(local $aq1 i32)(local $ak i32)(local $h i32)(local $length1 i32)(local $u1 i32)(local $t i32)(local $f1 i32)(local $a i32)(local $pos1 i32)(local $z1 i32)(local $as i32)(local $ap i32)(local $tmp3 i32)(local $q i32)(local $ac i32)(local $b i32)(local $r1 i32)(local $l i32)(local $al1 i32)(local $ai1 i32)
    block $entry
      global.get 0
      local.set $a
      global.get 0
      i32.const 0
      i32.store
      global.get 0
      i32.const 12
      i32.add
      global.set 0
      local.get $a
      i32.const 4
      i32.add
      i32.const 5
      i32.store
      local.get $a
      i32.const 8
      i32.add
      i32.const 12
      i32.store
      local.get $a
      local.set $pos1
      local.get $pos1
      i32.load
      i32.const 0
      i32.eq
      if
        local.get $pos1
        i32.const 4
        i32.add
        i32.load
        local.set $h
        local.get $h
        local.set $tmp2
        local.get $pos1
        i32.const 8
        i32.add
        i32.load
        local.set $i
        local.get $i
        local.set $tmp3
        block $k
          block $n
            block $am
              block $ae
                block $v
                  block $Match_tmp2
                    local.get $tmp2
                    i32.const 3
                    i32.eq
                    i32.const 1
                    i32.mul
                    local.get $tmp2
                    i32.const 12
                    i32.eq
                    i32.const 2
                    i32.mul
                    i32.add
                    local.get $tmp2
                    i32.const 5
                    i32.eq
                    i32.const 3
                    i32.mul
                    i32.add
                    local.get $tmp2
                    i32.const 4
                    i32.eq
                    i32.const 4
                    i32.mul
                    i32.add
                    br_table 4 3 2 1 0
                  end
                  local.get $tmp3
                  i32.const 3
                  i32.eq
                  if
                    i32.const 5
                    call $log
                    i32.const 0
                    local.set $ad1
                    local.get $ad1
                    local.set $ac
                    local.get $ac
                    local.set $b
                  else
                    i32.const 1
                    call $log
                    i32.const 0
                    local.set $z1
                    local.get $z1
                    local.set $y
                    local.get $y
                    local.set $b
                  end
                  br $k
                end
                local.get $tmp3
                i32.const 12
                i32.eq
                if
                  i32.const 13
                  call $log
                  i32.const 0
                  local.set $al1
                  local.get $al1
                  local.set $ak
                  local.get $ak
                  local.set $b
                else
                  i32.const 1
                  call $log
                  i32.const 0
                  local.set $ai1
                  local.get $ai1
                  local.set $ah
                  local.get $ah
                  local.set $b
                end
                br $k
              end
              local.get $tmp3
              i32.const 5
              i32.eq
              if
                i32.const 13
                call $log
                i32.const 0
                local.set $at1
                local.get $at1
                local.set $as
                local.get $as
                local.set $b
              else
                i32.const 1
                call $log
                i32.const 0
                local.set $aq1
                local.get $aq1
                local.set $ap
                local.get $ap
                local.set $b
              end
              br $k
            end
            local.get $tmp3
            i32.const 4
            i32.eq
            if
              i32.const 5
              call $log
              i32.const 0
              local.set $u1
              local.get $u1
              local.set $t
              local.get $t
              local.set $b
            else
              i32.const 1
              call $log
              i32.const 0
              local.set $r1
              local.get $r1
              local.set $q
              local.get $q
              local.set $b
            end
            br $k
          end
          i32.const 1
          call $log
          i32.const 0
          local.set $m1
          local.get $m1
          local.set $l
          local.get $l
          local.set $b
        end
      else
        i32.const 1
        call $log
        i32.const 0
        local.set $f1
        local.get $f1
        local.set $e
        local.get $e
        local.set $b
      end
    end
    local.get $b
    local.set $length1
  )
  (start $main)
)